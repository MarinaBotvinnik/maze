package test;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(5, 5); //Generate new maze
        System.out.println("the original maze");
        maze.printVer2();
        /**
        System.out.println("The next maze");
        byte [] arr = maze.toByteArray();
        Maze mazeVer2 = new Maze(arr);
        mazeVer2.printVer2();
         **/
        ////////////////////THIS IS THEIR TEST////////////////////////
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
        System.out.println();
        System.out.println("the new and sad version that is not working is:");
        //loadedMaze.printVer2();
        byte[] one = maze.toByteArray();
        for(int i=0; i<one.length; i++)
            System.out.print(one[i] + ", ");
        System.out.println();
        byte[] two = loadedMaze.toByteArray();
        for(int i=0; i<two.length; i++)
            System.out.print(two[i] + ", ");
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
//maze should be equal to loadedMaze
    }
}
