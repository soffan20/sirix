package org.sirix.io.file;

import org.sirix.api.PageReadOnlyTrx;
import org.sirix.exception.SirixIOException;
import org.sirix.io.bytepipe.ByteHandler;
import org.sirix.io.Reader;
import org.sirix.page.*;
import org.sirix.page.interfaces.Page;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapReader implements Reader {

    private FileReader fileReader = null;
    private MappedByteBufferHandler mDataBuffer = null;
    private MappedByteBufferHandler mRevisionOffsetBuffer = null;

    public MemoryMapReader(final RandomAccessFile dataFile, final RandomAccessFile revisionsOffsetFile,
                           final ByteHandler handler, final SerializationType serializationType,
                           final PagePersister pagePersister, MappedByteBufferHandler dataBuffer,
                           MappedByteBufferHandler revisionOffsetBuffer) throws IOException {

        fileReader = new FileReader(dataFile, revisionsOffsetFile, handler, serializationType, pagePersister);
        this.mDataBuffer = dataBuffer;
        this.mRevisionOffsetBuffer = revisionOffsetBuffer;
    }

    @Override
    public PageReference readUberPageReference() throws SirixIOException {
        final PageReference uberPageReference = new PageReference();
        // Read primary beacon.
        uberPageReference.setKey(mDataBuffer.readLong(0));

        final UberPage page = (UberPage) read(uberPageReference, null);
        uberPageReference.setPage(page);
        return uberPageReference;
    }

    @Override
    public Page read(@Nonnull PageReference reference, @Nullable PageReadOnlyTrx pageReadTrx) {
        try {
            // Read page from file.
            switch (fileReader.mType) {
                case DATA:
                    mDataBuffer.setOffset((int)reference.getKey());
                    break;
                case TRANSACTION_INTENT_LOG:
                    mDataBuffer.setOffset((int) reference.getPersistentLogKey());
                    break;
                default:
                    assert false; //Should not come here.
            }
            final int dataLength = mDataBuffer.readInt();
            reference.setLength(dataLength + FileReader.OTHER_BEACON);
            final byte[] page = new byte[dataLength];

            // Perform byte operations.
            final DataInputStream input =
                    new DataInputStream(fileReader.mByteHandler.deserialize(new ByteArrayInputStream(page)));

            // Return reader required to instantiate and deserialize page.
            return fileReader.mPagePersiter.deserializePage(input, pageReadTrx, fileReader.mType);
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    @Override
    public void close() throws SirixIOException {
        mDataBuffer.force();
        mRevisionOffsetBuffer.force();
        fileReader.close();
    }

    @Override
    public RevisionRootPage readRevisionRootPage(int revision, PageReadOnlyTrx pageReadTrx) {
        try {
            mRevisionOffsetBuffer.setOffset(revision*8);
            long positionToStartReadingFrom = mRevisionOffsetBuffer.readLong();

            mDataBuffer.setOffset((int)positionToStartReadingFrom);
            final int dataLength = mDataBuffer.readInt();
            final byte[] page = mDataBuffer.read(dataLength);

            // Perform byte operations.
            final DataInputStream input =
                    new DataInputStream(fileReader.mByteHandler.deserialize(new ByteArrayInputStream(page)));

            // Return reader required to instantiate and deserialize page.
            return (RevisionRootPage) fileReader.mPagePersiter.deserializePage(input, pageReadTrx, fileReader.mType);
        } catch (IOException e) {
            throw new SirixIOException(e);
        }
    }
}
