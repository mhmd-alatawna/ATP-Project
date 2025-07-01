package org.example.IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;
    int initialCount ;
    byte pack ;
    int count ;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        initialCount = 0 ;
        count = 0 ;
        pack = 0 ;
    }

    public void close() throws IOException {
        out.write(pack);
        super.close();
    }

    @Override
    public void flush() throws IOException {
        out.write(pack);
        super.flush();
    }
    @Override
    public void write(int b) throws IOException {
        if(initialCount < 24){
            out.write(b);
            initialCount++;
            return;
        }

        if(count == 8){
            out.write(pack);
            count = 0 ;
            pack = 0 ;
        }
        int bitPlace = 7 - count;
        if(b != 0)
            pack |= 1 << bitPlace ;
        count++;
    }
}
