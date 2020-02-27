package org.sirix.io.file;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

public class MappedByteBufferHandler {
    private MappedByteBuffer mappedByteBuffer = null;
    private int size = 0;
    private int offset = 0;

    /**
     * Creates a handler
     * @param mappedByteBuffer
     * @param size
     * @param offset
     */
    public MappedByteBufferHandler(MappedByteBuffer mappedByteBuffer, int size, int offset){
        this.mappedByteBuffer = mappedByteBuffer;
        this.size = size;
        this.offset = offset;
    }

    public void updateLimit(MappedByteBuffer mappedByteBuffer){
        this.force();
        this.mappedByteBuffer = mappedByteBuffer;
    }

    //Returns the limit of the underlying buffer.
    public int limit(){
        return mappedByteBuffer.limit();
    }

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
     * @param offset
     * @return
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

    public int size(){
        return size;
    }

    public int getOffset(){
        return offset;
    }

    /**
     * Write dataToBeWritten to the file. The size of the file will increase if the 'offset' + 'dataToBeWritten.length' are larger than size.
     * @param dataToBeWritten the bytes to write.
     * @return
     */
    public void write(byte[] dataToBeWritten){
        write(dataToBeWritten, offset);
    }

    public void writeLong(long longToBeWritten, int offset){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(longToBeWritten);
        write(buffer.array(), offset);
    }

    public void writeLong(long longToBeWritten){
        writeLong(longToBeWritten, offset);
    }

    public long readLong(int offset){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(read(8, offset));
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public long readLong(){
        return readLong(offset);
    }

    public void force(){
        mappedByteBuffer.limit(size);
        mappedByteBuffer.force();
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int readInt(){
        return ByteBuffer.wrap(read(4)).getInt();
    }

    public int readInt(int offset){
        return ByteBuffer.wrap(read(4,offset)).getInt();
    }

    public void setSize(int size){
        this.size = size;
    }
}
