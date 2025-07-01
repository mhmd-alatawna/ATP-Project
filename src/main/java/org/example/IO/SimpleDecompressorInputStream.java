package org.example.IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;
    private int initialCount ;
    private byte lastSeen ;
    private int toWriteCount ;
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
        lastSeen = (byte) 0;
        initialCount = 0;
        toWriteCount = -1;
    }


    @Override
    public void close() throws IOException {
        in.close();
        super.close();
    }

    @Override
    public int read() throws IOException {
        if(initialCount < 24){
            initialCount ++ ;
            return in.read() ;
        }
        if(toWriteCount == -1){
            int x = in.read() ;
            if(x == -1)
                return -1 ;
            lastSeen = (byte) x ;
            x = in.read() ;
            if(x == -1)
                return -1 ;
            toWriteCount = x ;
        }
        if(toWriteCount == 0){
            lastSeen = switchByte(lastSeen) ;
            int x = in.read() ;
            if(x == -1)
                return -1 ;
            toWriteCount = x;
        }
        toWriteCount -- ;
        return lastSeen & 0xFF;
    }

    @Override
    public int read(byte[] b) throws IOException {
        for(int i = 0 ; i < b.length ; i ++){
            int x = read() ;
            if(x == -1)
                return -1 ;
            b[i] = (byte) x ;
        }
        return 1 ;
    }
    private byte switchByte(int curr) {
        if(curr == 0)
            return 1;
        return 0;
    }


//    @Override
//    public int read(byte[] b) throws IOException {
//        for(int i = 0 ; i < 24 ; i ++){
//            b[i] = (byte) in.read();
//        }
//        if(b.length == 24)
//            return 1 ;
//        byte currByte = (byte) in.read();
//        int readedByte = in.read();
//        int index = 24 ;
//        while(readedByte != -1){
//            for(int i = 0 ; i < readedByte ; i ++){
//                b[index] = currByte;
//                index++;
//            }
//            currByte = switchByte(currByte);
//            readedByte = in.read();
//        }
//        return 1;
//    }
}
