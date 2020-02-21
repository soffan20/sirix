package org.sirix.io.file;

import org.sirix.access.ResourceConfiguration;
import org.sirix.exception.SirixIOException;
import org.sirix.io.Reader;
import org.sirix.io.Writer;
import org.sirix.io.bytepipe.ByteHandlePipeline;
import org.sirix.page.PagePersister;
import org.sirix.page.SerializationType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public final class MemoryMap extends FileStorage {

    RandomAccessFile randomAccessFile;

    public MemoryMap(ResourceConfiguration resourceConfiguration) throws FileNotFoundException {
        super(resourceConfiguration);
        int length = 0x8FFFFFF;

        randomAccessFile = new RandomAccessFile(this.FILENAME, "rw");
        /**
        try(RandomAccessFile file = new RandomAccessFile("howtodoinjava.dat", "rw"))
        {
            MappedByteBuffer out = file.getChannel()
                    .map(FileChannel.MapMode.READ_WRITE, 0, length);

            for (int i = 0; i < length; i++)
            {
                out.put((byte) 'x');
            }

            System.out.println("Finished writing");
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

    };

    @Override
    public Reader createReader() throws SirixIOException {
        try {
            final Path dataFilePath = createDirectoriesAndFile();
            final Path revisionsOffsetFilePath = getRevisionFilePath();

            return new MemoryMapReader(new RandomAccessFile(dataFilePath.toFile(), "r"),
                    new RandomAccessFile(revisionsOffsetFilePath.toFile(), "r"),
                    new ByteHandlePipeline(mByteHandler), SerializationType.DATA, new PagePersister());
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    @Override
    public Writer createWriter() throws SirixIOException {
        try {
            final Path dataFilePath = createDirectoriesAndFile();
            final Path revisionsOffsetFilePath = getRevisionFilePath();

            return new MemoryMapWriter(new RandomAccessFile(dataFilePath.toFile(), "rw"),
                    new RandomAccessFile(revisionsOffsetFilePath.toFile(), "rw"),
                    new ByteHandlePipeline(mByteHandler), SerializationType.DATA, new PagePersister());
        } catch (final IOException e) {
            throw new SirixIOException(e);
        }
    }

    @Override
    public void close(){};
}
