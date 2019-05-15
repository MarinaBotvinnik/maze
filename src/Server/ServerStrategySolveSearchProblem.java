package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private static volatile int index = 1;

    private Solution getSolutionFromTxt(String txt) {
        Solution solution = null;
        String[] states = txt.split("(|,|)");
        ArrayList<AState> arrSol = new ArrayList<>();
        int x = Integer.parseInt(states[0]);
        int y = Integer.parseInt(states[1]);
        double cost = Double.parseDouble(states[2]);
        MazeState first = new MazeState(x, y, cost);
        arrSol.add(0, first);
        for (int i = 3; i < states.length; i = i + 3) {
            x = Integer.parseInt(states[i]);
            y = Integer.parseInt(states[i + 1]);
            cost = Double.parseDouble(states[i + 2]);
            MazeState second = new MazeState(x, y, cost);
            second.setCameFrom(first);
            arrSol.add(0, second);
            first = new MazeState(x, y, cost);
        }
        solution.setSolutionPath(arrSol);
        return solution;
    }

    private Solution WriteMazeAndSol(String tmppathM, String tmppathS, Maze maze) {
        Solution solution = null;
        try {
            FileOutputStream fileM = new FileOutputStream(tmppathM);
            BufferedWriter writerS = new BufferedWriter(new PrintWriter(tmppathS));
            MyCompressorOutputStream comp = new MyCompressorOutputStream(fileM);
            SearchableMaze domain = new SearchableMaze(maze);
            BestFirstSearch searcher = new BestFirstSearch();
            solution = searcher.solve(domain);
            ArrayList<AState> path = solution.getSolutionPath();
            for (AState state : path) {
                String[] s = state.getStateName().split(" ");
                writerS.write("(" + s[0] + "," + s[1] + ")");
                int cost = (int) state.getCost();
                writerS.write(cost);
            }
            writerS.close();
            comp.write(maze.toByteArray());
            comp.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.getMessage();
        }
        return solution;
    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            Solution solution = null;
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            Maze maze = (Maze) fromClient.readObject();
            String mazeName = "" + maze.getRows() + "," + maze.getColumns() + "," + maze.getStartPosition().toString() + maze.getGoalPosition().toString();
            String mazeNameS = "" + maze.getRows() + "," + maze.getColumns() + "," + maze.getStartPosition().toString() + maze.getGoalPosition().toString() + "S";
            Path path = Paths.get(tempDirectoryPath + mazeName);
            //there is already folder bby this name
            if (Files.exists(path)) {
                //lets compress the maze we got
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                MyCompressorOutputStream outputStream = new MyCompressorOutputStream(byteArrayOutputStream);
                outputStream.write(maze.toByteArray());
                byte[] mymazebyte = byteArrayOutputStream.toByteArray();
                //create a buffer to check equality of maze
                byte[] theMaze = new byte[mymazebyte.length];
                MyDecompressorInputStream myDecompressor;
                FileInputStream fileInput;
                boolean isFileFound = false;
                //check all the mazes that saved already in the folder
                for (int i = 1; i <= index; i++) {
                    Path paths = Paths.get(tempDirectoryPath + mazeName + "\\" + mazeName + i + ".txt");
                    if (Files.exists(paths)) {
                        fileInput = new FileInputStream(tempDirectoryPath + mazeName + "\\" + mazeName + i + ".txt");
                        myDecompressor = new MyDecompressorInputStream(fileInput);
                        myDecompressor.read(theMaze);
                        if (Arrays.equals(theMaze, mymazebyte)) {
                            solution = getSolutionFromTxt(tempDirectoryPath + mazeName + "\\" + mazeNameS + i + ".txt");
                            isFileFound = true;
                            break;
                        }
                    }
                }
                //the file name exits but there is no equal maze
                if (isFileFound == false) {
                    solution = WriteMazeAndSol(tempDirectoryPath + mazeName + "\\" + mazeName + index + ".txt", tempDirectoryPath + mazeName + "\\" + mazeNameS + index + ".txt", maze);
                    index++;
                }
                //we need to create a new file in the folder
            } else {
                new File(tempDirectoryPath + mazeName).mkdir();
                solution = WriteMazeAndSol(tempDirectoryPath + mazeName + "\\" + mazeName + index + ".txt", tempDirectoryPath + mazeName + "\\" + mazeNameS + index+".txt", maze);
            }
            toClient.writeObject(solution);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
