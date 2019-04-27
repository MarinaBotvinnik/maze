package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Aviadjo on 3/26/2017.
 */
public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(1500, 1500); //Generate new maze
        //System.out.println(maze.getStartPosition());
        //System.out.println(maze.getGoalPosition());
        byte[] one = maze.toByteArray();
        Maze two = new Maze(one);
        byte[] tt = two.toByteArray();
        boolean is = Arrays.equals(two.toByteArray(), one);

        //System.out.println(is);

        /*for(int i=0; i<one.length; i++) {
            System.out.print(one[i] + " ,");
        }
        System.out.println();
        for (int j=0; j<tt.length ; j++){
            System.out.print(tt[j] + " ,");
        }*/

        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Maze loadedMaze = new Maze(savedMazeBytes);
        one= maze.toByteArray();
        byte[] l = loadedMaze.toByteArray();
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals)); //maze should be equal to loadedMaze
        for(int i=0; i<50; i++) {
            System.out.print(one[i] + " ,");
        }
        System.out.println();
        for (int j=0; j<50 ; j++) {
            System.out.print(l[j] + " ,");
        }


    }
}