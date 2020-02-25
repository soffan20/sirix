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
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 * This currenlty only supports up to 2 gb, due to limitation in buffer as they only support up to 2 gb.
 */
public final class MemoryMap implements Storage {

    RandomAccessFile randomAccessFile;
    private FileStorage fileStorage = null;
    private MappedByteBufferHandler mDataHandler = null;
    private MappedByteBufferHandler mRevisionOffsetHandler = null;

    public MemoryMap(ResourceConfiguration resourceConfiguration) throws IOException {
        fileStorage = new FileStorage(resourceConfiguration);
        randomAccessFile = new RandomAccessFile(fileStorage.FILENAME, "rw");

        final Path dataFilePath = fileStorage.createDirectoriesAndFile();
        final Path revisionsOffsetFilePath = fileStorage.getRevisionFilePath();
        FileWriter fileWriter =  new FileWriter(new RandomAccessFile(dataFilePath.toFile(), "r"),
                new RandomAccessFile(revisionsOffsetFilePath.toFile(), "r"),
                new ByteHandlePipeline(fileStorage.mByteHandler), SerializationType.DATA, new PagePersister());

        FileChannel dataFileChannel = fileWriter.mDataFile.getChannel();
        MappedByteBuffer temp = dataFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, dataFileChannel.size());
        mDataHandler = new MappedByteBufferHandler(temp, (int) dataFileChannel.size());

        FileChannel revisionOffsetChannel = fileWriter.mRevisionsOffsetFile.getChannel();
        temp = revisionOffsetChannel.map(FileChannel.MapMode.READ_WRITE, 0, revisionOffsetChannel.size());
        mRevisionOffsetHandler = new MappedByteBufferHandler(temp, (int) revisionOffsetChannel.size());
    };

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
                    mDataHandler, mRevisionOffsetHandler);
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
