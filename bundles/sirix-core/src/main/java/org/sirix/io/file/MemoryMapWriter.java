package org.sirix.io.file;

import org.sirix.api.PageReadOnlyTrx;
import org.sirix.exception.SirixIOException;
import org.sirix.io.Writer;
import org.sirix.io.bytepipe.ByteHandler;
import org.sirix.io.file.FileReader;
import org.sirix.io.file.FileWriter;
import org.sirix.page.*;
import org.sirix.page.interfaces.Page;
import org.sirix.io.Writer;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapWriter implements Writer {
    private MemoryMapReader reader;
    private MappedByteBufferHandler mDataBuffer = null;
    private MappedByteBufferHandler mRevisionOffsetBuffer = null;

    private FileWriter fileWriter = null;

    public MemoryMapWriter(final RandomAccessFile dataFile, final RandomAccessFile revisionsOffsetFile,
                           final ByteHandler handler, final SerializationType serializationType,
                           final PagePersister pagePersister, MappedByteBufferHandler dataBuffer,
                           MappedByteBufferHandler revisionOffsetBuffer) throws IOException {

        fileWriter = new FileWriter(dataFile, revisionsOffsetFile, handler, serializationType, pagePersister);
        this.mDataBuffer = dataBuffer;
        this.mRevisionOffsetBuffer = revisionOffsetBuffer;



        reader = new MemoryMapReader(dataFile,revisionsOffsetFile,handler,serializationType,pagePersister, dataBuffer, revisionOffsetBuffer);
    }

    @Override
    public Writer write(PageReference pageReference) throws SirixIOException {
        // Perform byte operations.
        try {
            // Serialize page.
            final Page page = pageReference.getPage();
            assert page != null;

            final byte[] serializedPage;

            try (final ByteArrayOutputStream output = new ByteArrayOutputStream();
                 final DataOutputStream dataOutput =
                         new DataOutputStream(fileWriter.mReader.mByteHandler.serialize(output))) {
                fileWriter.mPagePersister.serializePage(dataOutput, page, fileWriter.mType);
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
            final long fileSize = mDataBuffer.size();
            final long offset = fileSize == 0
                    ? FileReader.FIRST_BEACON
                    : fileSize;

            mDataBuffer.write(writtenPage, (int) offset);
            // Remember page coordinates.
            switch (fileWriter.mType) {
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
            pageReference.setHash(fileWriter.mReader.mHashFunction.hashBytes(writtenPage).asBytes());

            if (fileWriter.mType == SerializationType.DATA && page instanceof RevisionRootPage) {
                mRevisionOffsetBuffer.writeLong(offset);
            }

            return this;
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    @Override
    public Writer writeUberPageReference(PageReference pageReference) throws SirixIOException {
        write(pageReference);
        mDataBuffer.writeLong(pageReference.getKey(), 0);
        return this;
    }

    @Override
    public Writer truncateTo(int revision) {
        return null;
    }

    @Override
    public Writer truncate() {
        return null;
    }


    @Override
    public PageReference readUberPageReference() throws SirixIOException {
        return reader.readUberPageReference();
    }

    @Override
    public Page read(PageReference key, @Nullable PageReadOnlyTrx pageReadTrx) throws SirixIOException {
        return reader.read(key, pageReadTrx);
    }

    @Override
    public void close() throws SirixIOException {
        mDataBuffer.force();
        mRevisionOffsetBuffer.force();
    }

    @Override
    public RevisionRootPage readRevisionRootPage(int revision, PageReadOnlyTrx pageReadTrx) {
        return null;
    }

}
