package org.sirix.io.file;

import org.sirix.access.ResourceConfiguration;
import org.sirix.exception.SirixIOException;
import org.sirix.io.Reader;
import org.sirix.io.Storage;
import org.sirix.io.Writer;
import org.sirix.io.bytepipe.ByteHandlePipeline;
import org.sirix.io.bytepipe.ByteHandler;
import org.sirix.page.PagePersister;
import org.sirix.page.SerializationType;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This creates a virtual file which can be written and read to.
 * Currently only supports up to 2 gb, due to limitation in how buffer works.
 */
public final class MemoryMap implements Storage {

    RandomAccessFile dataFile = null;
    RandomAccessFile revisionOffsetFile = null;
    private FileStorage fileStorage = null;
    private MappedByteBufferHandler mDataHandler = null;
    private MappedByteBufferHandler mRevisionOffsetHandler = null;

    /**
     * The constructor
     * @param resourceConfiguration the settings for the path to the file, revision file and their name and
     *                              possible other settings.
     * @throws SirixIOException thrown if an IOException occurs.
     */
    public MemoryMap(ResourceConfiguration resourceConfiguration) throws SirixIOException {
        fileStorage = new FileStorage(resourceConfiguration);
        try {
            dataFile = new RandomAccessFile(fileStorage.createDirectoriesAndFile().toFile(), "rw");
            revisionOffsetFile = new RandomAccessFile(FileStorage.REVISIONS_FILENAME, "rw");

            FileChannel dataFileChannel = dataFile.getChannel();
            MappedByteBuffer temp = dataFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, dataFile.length());
            mDataHandler = new MappedByteBufferHandler(temp, (int) dataFileChannel.size());

            FileChannel revisionOffsetChannel = revisionOffsetFile.getChannel();
            temp = revisionOffsetChannel.map(FileChannel.MapMode.READ_WRITE, 0, revisionOffsetFile.length());
            mRevisionOffsetHandler = new MappedByteBufferHandler(temp, (int) revisionOffsetChannel.size());
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }


    /**
     * Creates a reader for reading the file and possible the revision file.
     * @return a MemoryMapReader, for reading the file and revision file.
     * @throws SirixIOException if an IOException occurs.
     */
    @Override
    public Reader createReader() throws SirixIOException {
        try {
            final Path dataFilePath = fileStorage.createDirectoriesAndFile();
            final Path revisionsOffsetFilePath = fileStorage.getRevisionFilePath();

            return new MemoryMapReader(new RandomAccessFile(dataFilePath.toFile(), "r"),
                    new RandomAccessFile(revisionsOffsetFilePath.toFile(), "r"),
                    new ByteHandlePipeline(fileStorage.mByteHandler), SerializationType.DATA, new PagePersister(),
                    mDataHandler, mRevisionOffsetHandler);

        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    /**
     * Creates a writer for reading and writing to the file and revision file.
     * @return a MemoryMapWriter.
     * @throws SirixIOException if an IOException occurs.
     */
    @Override
    public Writer createWriter() throws SirixIOException {
        try {
            final Path dataFilePath = fileStorage.createDirectoriesAndFile();
            final Path revisionsOffsetFilePath = fileStorage.getRevisionFilePath();

            return new MemoryMapWriter(new RandomAccessFile(dataFilePath.toFile(), "rw"),
                    new RandomAccessFile(revisionsOffsetFilePath.toFile(), "rw"),
                    new ByteHandlePipeline(fileStorage.mByteHandler), SerializationType.DATA, new PagePersister(),
                    mDataHandler, mRevisionOffsetHandler, this);
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    /**
     * This function is not and should not be used.
     */
    @Override
    public void close() {
    }

    /**
     * Check if the underlying file actually exists.
     * @return true if it exists, otherwise false.
     * @throws SirixIOException if an IOException occurs.
     */
    @Override
    public boolean exists() throws SirixIOException {
        final Path storage = fileStorage.getDataFilePath();
        try {
            return Files.exists(storage) && Files.size(storage) > 0;
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    /**
     *
     * @return the bytehandler for the file.
     */
    @Override
    public ByteHandler getByteHandler() {
        return fileStorage.mByteHandler;
    }

    /**
     * Updates the size of the buffer limit is too small. It writes to the file in the normal memory
     * in the process of doing this. Increases the size by a factor of a power of 2, ex 2 times larger or 4 times
     * larger.
     * @param handler The handler sent in, should either be datahandler or the revisionoffsethandler.
     * @param limit How much memory the new buffer needs at least.
     * @return the new handler sent in.
     */
    public MappedByteBufferHandler updateSize(MappedByteBufferHandler handler, long limit) {
        MappedByteBufferHandler newHandler;
        RandomAccessFile fileToUpdateHandlerWith;
        if (handler.equals(mDataHandler)) {
            newHandler = mDataHandler;
            fileToUpdateHandlerWith = dataFile;
        } else {
            newHandler = mRevisionOffsetHandler;
            fileToUpdateHandlerWith = revisionOffsetFile;
        }
        try {
            int oldSize = (int) newHandler.size();
            int newSize = oldSize;
            while (newSize < limit) {
                newSize = 2 * oldSize;
            }
            FileChannel newChannel = fileToUpdateHandlerWith.getChannel();
            MappedByteBuffer temp = newChannel.map(FileChannel.MapMode.READ_WRITE, 0, dataFile.length());
            newHandler.replaceBuffer(temp);
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
        return newHandler;
    }

}
