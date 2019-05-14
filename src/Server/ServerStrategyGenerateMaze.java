package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{

    public ServerStrategyGenerateMaze() {

    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            int []sizes=(int[])fromClient.readObject();
            int rows = sizes[0];
            int columns = sizes[1];
            MyMazeGenerator mg = new MyMazeGenerator();
            Maze maze = mg.generate(rows,columns);
            byte []b = maze.toByteArray();
            MyCompressorOutputStream comp = new MyCompressorOutputStream(outToClient);
            comp.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
