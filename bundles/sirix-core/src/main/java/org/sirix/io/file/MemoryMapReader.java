package org.sirix.io.file;

import org.sirix.api.PageReadOnlyTrx;
import org.sirix.exception.SirixIOException;
import org.sirix.io.bytepipe.ByteHandler;
import org.sirix.io.Reader;
import org.sirix.page.PagePersister;
import org.sirix.page.PageReference;
import org.sirix.page.RevisionRootPage;
import org.sirix.page.SerializationType;
import org.sirix.page.interfaces.Page;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapReader implements Reader {

    private FileReader fileReader = null;
    private MappedByteBuffer mDataBuffer = null;
    private MappedByteBuffer mRevisionOffsetBuffer = null;

    public MemoryMapReader(final RandomAccessFile dataFile, final RandomAccessFile revisionsOffsetFile,
                           final ByteHandler handler, final SerializationType type,
                           final PagePersister pagePersistenter) throws IOException {
        fileReader = new FileReader(dataFile, revisionsOffsetFile, handler, type, pagePersistenter);
        FileChannel dataFileChannel = fileReader.mDataFile.getChannel();
        mDataBuffer = dataFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, dataFileChannel.size());
        FileChannel revisionOffsetFileChannel = fileReader.mRevisionsOffsetFile.getChannel();
        mRevisionOffsetBuffer =
                revisionOffsetFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, revisionOffsetFileChannel.size());
    }

    private byte[] readNBytesFromMappedByteBuffer(MappedByteBuffer buffer, int nBytes){
        byte[] dataLengthAsBytes = new byte[nBytes];
        for(int i = 0; i < dataLengthAsBytes.length; i++){
            dataLengthAsBytes[i] = buffer.get();
        }
        return dataLengthAsBytes;
    }

    @Override
    public PageReference readUberPageReference() throws SirixIOException {
        final PageReference uberPageReference = new PageReference();
        try{
            
        }
    }

    @Override
    public Page read(@Nonnull PageReference reference, @Nullable PageReadOnlyTrx pageReadTrx) {
        try {
            // Read page from file.
            switch (fileReader.mType) {
                case DATA:
                    mDataBuffer = mDataBuffer.position((int)reference.getKey());
                    break;
                case TRANSACTION_INTENT_LOG:
                    mDataBuffer = mDataBuffer.position((int) reference.getPersistentLogKey());
                    break;
                default:
                    assert false; //Should not come here.
            }
            final int dataLength = ByteBuffer.wrap(readNBytesFromMappedByteBuffer(mDataBuffer, 4)).getInt();
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
            mRevisionOffsetBuffer.position(revision*8);
            long positionToStartReadingFrom =
                    ByteBuffer.wrap(readNBytesFromMappedByteBuffer(mRevisionOffsetBuffer, 8)).getLong();
            mDataBuffer.position((int)positionToStartReadingFrom);
            final int dataLength = ByteBuffer.wrap(readNBytesFromMappedByteBuffer(mDataBuffer, 4)).getInt();
            final byte[] page = readNBytesFromMappedByteBuffer(mDataBuffer, dataLength);

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
