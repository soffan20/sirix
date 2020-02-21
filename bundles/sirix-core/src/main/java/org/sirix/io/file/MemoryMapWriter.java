package org.sirix.io.file;

import org.sirix.exception.SirixIOException;
import org.sirix.io.Writer;
import org.sirix.io.bytepipe.ByteHandler;
import org.sirix.io.file.FileReader;
import org.sirix.io.file.FileWriter;
import org.sirix.page.PagePersister;
import org.sirix.page.PageReference;
import org.sirix.page.RevisionRootPage;
import org.sirix.page.SerializationType;
import org.sirix.page.interfaces.Page;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapWriter extends FileWriter {
    public MemoryMapWriter(final RandomAccessFile dataFile, final RandomAccessFile revisionsOffsetFile,
                           final ByteHandler handler, final SerializationType serializationType,
                           final PagePersister pagePersister){
        super(dataFile, revisionsOffsetFile, handler, serializationType, pagePersister);
    }

    @Override
    public Writer writeUberPageReference(PageReference pageReference) throws SirixIOException {
        // Perform byte operations.
        try {
            // Serialize page.
            final Page page = pageReference.getPage();
            assert page != null;

            final byte[] serializedPage;

            try (final ByteArrayOutputStream output = new ByteArrayOutputStream();
                 final DataOutputStream dataOutput =
                         new DataOutputStream(mReader.mByteHandler.serialize(output))) {
                mPagePersister.serializePage(dataOutput, page, mType);
                dataOutput.flush();
                serializedPage = output.toByteArray();
            }

            final byte[] writtenPage = new byte[serializedPage.length + FileReader.OTHER_BEACON];
            final ByteBuffer buffer = ByteBuffer.allocate(writtenPage.length);
            buffer.putInt(serializedPage.length);
            buffer.put(serializedPage);
            buffer.position(0);
            buffer.get(writtenPage, 0, writtenPage.length);

            // Getting actual offset and appending to the end of the current file.
            final long fileSize = mDataFile.length();
            final long offset = fileSize == 0
                    ? FileReader.FIRST_BEACON
                    : fileSize;

            FileChannel fileChannel = mDataFile.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileChannel.size());
            mappedByteBuffer.put(writtenPage);

            // Remember page coordinates.
            switch (mType) {
                case DATA:
                    pageReference.setKey(offset);
                    break;
                case TRANSACTION_INTENT_LOG:
                    pageReference.setPersistentLogKey(offset);
                    break;
                default:
                    // Must not happen.
            }

            pageReference.setLength(writtenPage.length);
            pageReference.setHash(mReader.mHashFunction.hashBytes(writtenPage).asBytes());

            if (mType == SerializationType.DATA && page instanceof RevisionRootPage) {
                FileChannel fileChannelRevisioned = mRevisionsOffsetFile.getChannel();
                MappedByteBuffer mappedByteBufferRevisioned = fileChannelRevisioned.map(FileChannel.MapMode.READ_WRITE, mRevisionsOffsetFile.length(), writtenPage.length);
                mappedByteBufferRevisioned.putLong(offset);
            }

            return this;
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }
}
