package org.example.IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in ;
    private int initialCount ;
    private int[] pack ;
    private int packIndex ;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        initialCount = 0 ;
        pack = new int[8] ;
        packIndex = -1 ;
    }


    @Override
    public void close() throws IOException {
        in.close();
        super.close();
    }

    @Override
    public int read() throws IOException {
        if(initialCount < 24){
            initialCount++ ;
            return in.read();
        }
        if(packIndex == 8 || packIndex == -1){
            int x = in.read() ;
            if(x == -1)
                return -1 ;
            byte tmp = (byte) x ;
            for(int j = 0 ; j < 8 ; j ++){
                int shift = 7 - j ;
                pack[j] = ((tmp >> shift) & 0x01) & 0xFF;
            }
            packIndex = 0 ;
        }
        packIndex ++ ;
        return pack[packIndex - 1] ;
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


//    @Override
//    public int read(byte[] b) throws IOException {
//        for(int i = 0 ; i < 24 ; i ++){
//            b[i] = (byte) in.read() ;
//        }
//
//        //notice that in the compressor a pack is added when stream is closed thus there might be extra information
//        //which we should ignore when decompressing
//        int position = 24 ;
//        for(int i = 24 ; i < b.length ; i ++){
//            byte tmp = (byte) in.read() ;
//            for(int j = 0 ; j < 8 && position < b.length; j ++){
//                int shift = 7 - j ;
//                b[position] = (byte) ((tmp >> shift) & 0x01) ;
//                position ++ ;
//            }
//        }
//        return 1 ;
//    }
}
