package org.sirix.io.memoryMapped;

import org.sirix.api.PageReadOnlyTrx;
import org.sirix.exception.SirixIOException;
import org.sirix.io.bytepipe.ByteHandler;
import org.sirix.io.file.FileReader;
import org.sirix.page.PagePersister;
import org.sirix.page.PageReference;
import org.sirix.page.SerializationType;
import org.sirix.page.interfaces.Page;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapReader extends FileReader {

    public MemoryMapReader(final RandomAccessFile dataFile, final RandomAccessFile revisionsOffsetFile,
                           final ByteHandler handler, final SerializationType type,
                           final PagePersister pagePersistenter){
        super(dataFile, revisionsOffsetFile, handler, type, pagePersistenter);
    }

    private int readIntFromMappedByteBuffer(MappedByteBuffer buffer){
        byte[] dataLengthAsBytes = new byte[4];
        for(int i = 0; i < dataLengthAsBytes.length; i++){
            dataLengthAsBytes[i] = buffer.get();
        }
        return ByteBuffer.wrap(dataLengthAsBytes).getInt();
    }

    @Override
    public Page read(@Nonnull PageReference reference, @Nullable PageReadOnlyTrx pageReadTrx) {
        try {
            // Read page from file.
            FileChannel fileChannel = mDataFile.getChannel();
            MappedByteBuffer buffer = null;
            switch (mType) {
                case DATA:
                    buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, reference.getKey(), fileChannel.size());
                    break;
                case TRANSACTION_INTENT_LOG:
                    buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, reference.getPersistentLogKey(), fileChannel.size());
                    break;
                default:
                    assert false; //Should not come here.
            }
            final int dataLength = readIntFromMappedByteBuffer(buffer);
            reference.setLength(dataLength + FileReader.OTHER_BEACON);
            final byte[] page = new byte[dataLength];


            // Perform byte operations.
            final DataInputStream input =
                    new DataInputStream(mByteHandler.deserialize(new ByteArrayInputStream(page)));

            // Return reader required to instantiate and deserialize page.
            return mPagePersiter.deserializePage(input, pageReadTrx, mType);
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }
}
