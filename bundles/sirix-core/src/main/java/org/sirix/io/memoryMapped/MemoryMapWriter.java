package org.sirix.io.memoryMapped;

import org.sirix.exception.SirixIOException;
import org.sirix.io.Writer;
import org.sirix.io.bytepipe.ByteHandler;
import org.sirix.io.file.FileWriter;
import org.sirix.page.PagePersister;
import org.sirix.page.PageReference;
import org.sirix.page.SerializationType;

import java.io.RandomAccessFile;

public class MemoryMapWriter extends FileWriter {
    public MemoryMapWriter(final RandomAccessFile dataFile, final RandomAccessFile revisionsOffsetFile,
                           final ByteHandler handler, final SerializationType serializationType,
                           final PagePersister pagePersister){
        super(dataFile, revisionsOffsetFile, handler, serializationType, pagePersister);
    }

    @Override
    public Writer writeUberPageReference(PageReference pageReference) throws SirixIOException {
        return super.writeUberPageReference(pageReference);
    }
}
