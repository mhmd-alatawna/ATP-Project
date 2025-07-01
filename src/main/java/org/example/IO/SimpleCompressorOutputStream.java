package org.example.IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;
    int lastSeen ;
    int count ;
    int initialCount ;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out ;
        lastSeen = -1 ;
        count = 0 ;
        initialCount = 0 ;
    }

    @Override
    public void close() throws IOException {
        if(count != 0)
            out.write(count);
        super.close();
    }
    @Override
    public void write(int b) throws IOException {
        //first 24 bytes represent meta-data about the maze and can't be compressed with this method
        //just ignore the first 24 bytes and compress the rest
        if(initialCount < 24){
            out.write(b);
            initialCount++;
            return;
        }

        //here the algorithm starts
        if(lastSeen == -1){
            lastSeen = b ;
            count = 1 ;
            out.write(b) ;
            return;
        }

        if(b == lastSeen){
            count++;
            if(count == 255){
                out.write(count);
                count = 0 ;
                lastSeen = switchByte(lastSeen) ;
            }
        }else{
            out.write(count);
            lastSeen = b ;
            count = 1 ;
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        for (byte value : b) {
            this.write(value);
        }
    }

    private byte switchByte(int curr) {
        if(curr == 0)
            return 1;
        return 0;
    }
}
