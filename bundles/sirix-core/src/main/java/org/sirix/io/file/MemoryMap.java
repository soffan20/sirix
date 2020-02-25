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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

/**
 * This currenlty only supports up to 2 gb, due to limitation in buffer as they only support up to 2 gb.
 */
public final class MemoryMap implements Storage {

    RandomAccessFile randomAccessFile;
    private FileStorage fileStorage = null;
    public MemoryMap(ResourceConfiguration resourceConfiguration) throws FileNotFoundException {
        fileStorage = new FileStorage(resourceConfiguration);
        randomAccessFile = new RandomAccessFile(fileStorage.FILENAME, "rw");
    };

    @Override
    public Reader createReader() throws SirixIOException {
        try {
            final Path dataFilePath = fileStorage.createDirectoriesAndFile();
            final Path revisionsOffsetFilePath = fileStorage.getRevisionFilePath();

            return new MemoryMapReader(new RandomAccessFile(dataFilePath.toFile(), "r"),
                    new RandomAccessFile(revisionsOffsetFilePath.toFile(), "r"),
                    new ByteHandlePipeline(fileStorage.mByteHandler), SerializationType.DATA, new PagePersister());
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
                    new ByteHandlePipeline(fileStorage.mByteHandler), SerializationType.DATA, new PagePersister());
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    @Override
    public void close(){}

    @Override
    public boolean exists() throws SirixIOException {
        return false;
    }

    @Override
    public ByteHandler getByteHandler() {
        return null;
    }

    ;
}
