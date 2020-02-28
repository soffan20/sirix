package org.sirix.io.file;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

/**
 * A handler for a MappedByteBuffer, used to keep track of written size and offset.
 */
public class MappedByteBufferHandler {
    private MappedByteBuffer mappedByteBuffer = null;
    private int size = 0;
    private int offset = 0;

    /**
     * Creates a handler
     * @param mappedByteBuffer the buffer to wrap.
     * @param size the initial size
     * @param offset the initial offset
     */
    public MappedByteBufferHandler(MappedByteBuffer mappedByteBuffer, int size, int offset){
        this.mappedByteBuffer = mappedByteBuffer;
        this.size = size;
        this.offset = offset;
    }

    /**
     * Used to replace the buffer, writes the original one to the hard drive.
     * @param mappedByteBuffer the buffer which replaces the current one.
     */
    public void replaceBuffer(MappedByteBuffer mappedByteBuffer){
        this.force();
        this.mappedByteBuffer = mappedByteBuffer;
    }

    //Returns the limit of the underlying buffer.
    public int limit(){
        return mappedByteBuffer.limit();
    }

    /**
     * Constructor
     * @param mappedByteBuffer the underlying buffer
     * @param size the inital size of the buffer.
     */
    public MappedByteBufferHandler(MappedByteBuffer mappedByteBuffer, int size){
        this(mappedByteBuffer, size, 0);
    }

    /**
     * Reads nBytes from the offset. The new offset will be the 'offset' given + the length of bytes read.
     * @param offset the starting position to read from.
     * @param nBytes the amount of bytes to read.
     * @return the read bytes.
     */
    public byte[] read(int nBytes, int offset){
        if(offset < 0 || nBytes < 0)
            throw new IllegalArgumentException("Offset and nBytes must be not be negative");
        //Offset can't be larger than size.
        if(offset + nBytes >= size)
            throw new IndexOutOfBoundsException("Offset + read bytes are larger than the size.");
        byte[] readBytes = new byte[nBytes];

        //Update the offset.
        this.offset = offset;
        mappedByteBuffer.position(offset);

        for(int i = 0; i < nBytes; i++)
            readBytes[i] = mappedByteBuffer.get();
        return readBytes;
    }

    /**
     * Reads nBytes from the offset. The new offset will be the 'offset' given + the length of bytes read.
     * @param nBytes the amount of bytes to read.
     * @return the read bytes.
     */
    public byte[] read(int nBytes){
        return read(nBytes, offset);
    }

    /**
     * Write dataToBeWritten to the file. The size of the file will increase if the 'offset' + 'dataToBeWritten.length' are larger than size.
     * @param dataToBeWritten the bytes to write.
     * @param offset the offset where to start writing from.
     */
    public void write(byte[] dataToBeWritten, int offset){
        //We can't have negative offset.
        if(offset < 0 || dataToBeWritten == null)
            throw new IllegalArgumentException("Offset must be not be negative and dataToBeWritten most not be null.");

        mappedByteBuffer.put(dataToBeWritten);

        //Updates size.
        size = Math.max(dataToBeWritten.length + offset, size);

        //Updates offset.
        offset = offset + dataToBeWritten.length;
        mappedByteBuffer.position(offset);

    }

    /**
     *
     * @return the size of the function.
     */
    public int size(){
        return size;
    }

    /**
     *
     * @return the current offset.
     */
    public int getOffset(){
        return offset;
    }

    /**
     * Write dataToBeWritten to the file. The size of the file will increase if the 'offset' + 'dataToBeWritten.length' are larger than size.
     * @param dataToBeWritten the bytes to write.
     */
    public void write(byte[] dataToBeWritten){
        write(dataToBeWritten, offset);
    }

    /**
     * Writes a long to the file.
     * @param longToBeWritten the long to be written.
     * @param offset the offset to write from.
     */
    public void writeLong(long longToBeWritten, int offset){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(longToBeWritten);
        write(buffer.array(), offset);
    }

    /**
     * Writes a long to the file.
     * @param longToBeWritten the long to be written.
     */
    public void writeLong(long longToBeWritten){
        writeLong(longToBeWritten, offset);
    }

    /**
     * Read long from the file.
     * @param offset the offset to read from.
     * @return the long read from the file.
     */
    public long readLong(int offset){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(read(8, offset));
        buffer.flip();//need flip
        return buffer.getLong();
    }

    /**
     * Read a long
     * @return the long read.
     */
    public long readLong(){
        return readLong(offset);
    }

    /**
     * Writes the size to the harddrive. Should not be used after this.
     */
    public void force(){
        mappedByteBuffer.limit(size);
        mappedByteBuffer.force();
    }

    /**
     * Ses the current offset.
     * @param offset the new offset.
     */
    public void setOffset(int offset){
        this.offset = offset;
    }

    /**
     * Reads an int from the file.
     * @return the int read from the file.
     */
    public int readInt(){
        return ByteBuffer.wrap(read(4)).getInt();
    }

    /**
     * Reads a int from the file starting at the offset..
     * @param offset the offset to start reading from
     * @return the read int.
     */
    public int readInt(int offset){
        return ByteBuffer.wrap(read(4,offset)).getInt();
    }

    /**
     * Sets the size.
     * @param size the new size.
     */
    public void setSize(int size){
        this.size = size;
    }
}
