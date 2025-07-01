package org.example.atpprojectpartc.Model.ClientStrategies;

import org.example.Client.IClientStrategy;
import org.example.IO.MyDecompressorInputStream;
import org.example.algorithms.mazeGenerators.Maze;

import java.io.*;

public class MazeGeneratingStrategy implements IClientStrategy {
    int rows , columns ;
    Maze maze ;
    public MazeGeneratingStrategy(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        maze = null ;
    }

    @Override
    public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
            ObjectInputStream fromServer = new ObjectInputStream(inputStream);
            toServer.flush();
            int[] mazeDimensions = new int[]{rows, columns};
            toServer.writeObject(mazeDimensions); //send maze dimensions to server
            toServer.flush();
            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
            byte[] decompressedMaze = new byte[mazeDimensions[0] * mazeDimensions[1] + 24]; //allocating byte[] for the decompressed maze
            is.read(decompressedMaze); //Fill decompressedMaze
            maze = new Maze(decompressedMaze);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Maze getMaze() {
        return maze;
    }
}
