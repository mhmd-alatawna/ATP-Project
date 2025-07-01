package org.example.Server;

import org.example.algorithms.mazeGenerators.*;
import org.example.IO.MyCompressorOutputStream;

import java.io.*;
public class ServerStrategyGenerateMaze implements IServerStrategy {
    public ServerStrategyGenerateMaze() {}

    @Override
    public void serverstrategy(InputStream input, OutputStream output) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(output);
        ObjectInputStream inputStream = new ObjectInputStream(input);
        int[] mazeSize ;
        try {
            mazeSize = (int[]) inputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        IMazeGenerator generator = new MyMazeGenerator();
        String str = Configurations.getInstance().getMazeGeneratingAlgorithm();
        if(str.equals("SimpleMazeGenerator"))
            generator = new SimpleMazeGenerator();
        if(str.equals("EmptyMazeGenerator"))
            generator = new EmptyMazeGenerator();

        Maze maze = generator.generate(mazeSize[0], mazeSize[1]);
        byte[] arr = maze.toByteArray() ;
        ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
        MyCompressorOutputStream compressor = new MyCompressorOutputStream(memoryBuffer);
        compressor.write(arr);
        compressor.close();
        byte[] compressed = memoryBuffer.toByteArray();
        outputStream.writeObject(compressed);
    }
}
