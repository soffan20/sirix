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
 * This currenlty only supports up to 2 gb, due to limitation in buffer as they only support up to 2 gb.
 */
public final class MemoryMap implements Storage {

    RandomAccessFile dataFile = null;
    RandomAccessFile revisionOffsetFile = null;
    private FileStorage fileStorage = null;
    private MappedByteBufferHandler mDataHandler = null;
    private MappedByteBufferHandler mRevisionOffsetHandler = null;

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

    ;

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

    @Override
    public void close() {
    }

    @Override
    public boolean exists() throws SirixIOException {
        final Path storage = fileStorage.getDataFilePath();
        try {
            return Files.exists(storage) && Files.size(storage) > 0;
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    @Override
    public ByteHandler getByteHandler() {
        return fileStorage.mByteHandler;
    }

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
            newHandler.updateLimit(temp);
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
        return newHandler;
    }

}
