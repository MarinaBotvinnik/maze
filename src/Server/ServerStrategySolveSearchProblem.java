package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ServerStrategySolveSearchProblem implements IServerStrategy{

    private Solution getSolutionFromTxt(String txt){
        Solution solution = null;
        String[] states = txt.split("(|,|)");
        ArrayList<AState> arrSol = new ArrayList<>();
        int x = Integer.parseInt(states[0]);
        int y = Integer.parseInt(states[1]);
        double cost = Double.parseDouble(states[2]);
        MazeState first = new MazeState(x,y,cost);
        arrSol.add(0,first);
        for(int i=3;i<states.length; i = i+3){
            x = Integer.parseInt(states[i]);
            y = Integer.parseInt(states[i+1]);
            cost = Double.parseDouble(states[i+2]);
            MazeState second = new MazeState(x,y,cost);
            second.setCameFrom(first);
            arrSol.add(0,second);
            first=new MazeState(x,y,cost);
        }
        solution.setSolutionPath(arrSol);
        return solution;
    }
    private Solution WriteMazeAndSol(String tmppath,Maze maze){
        Solution solution=null;
        try {
            /*
            FileOutputStream outS=new FileOutputStream(tempDirectoryPath+"//"+maze.getStartPosition().getRowIndex()+maze.getStartPosition().getColumnIndex()+maze.getGoalPosition().getRowIndex()+maze.getGoalPosition().getColumnIndex()+maze.getRows()+maze.getcolumns()+"//"+index+"s.txt");
            BufferedWriter writer = new BufferedWriter(new PrintWriter(outS));
            ArrayList<AState> solutionPath = solution.getSolutionPath();
            for (int i = 0; i < solutionPath.size(); i++) {
                writer.write(solutionPath.get(i).toString());
                writer.newLine();
            }
             */
            FileOutputStream file = new FileOutputStream(tmppath);
            BufferedWriter writer = new BufferedWriter(new PrintWriter(file));
            MyCompressorOutputStream comp = new MyCompressorOutputStream(file);
            comp.write(maze.toByteArray());
            writer.write(":");
            SearchableMaze domain = new SearchableMaze(maze);
            BestFirstSearch searcher = new BestFirstSearch();
            solution = searcher.solve(domain);
            ArrayList<AState> path = solution.getSolutionPath();
            for (AState state : path) {
                String[] s = state.getStateName().split(" ");
                writer.write("(" + s[0] + "," + s[1] + ")");
                double cost = state.getCost();
                writer.write((int) cost);
            }
            writer.newLine();
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
            Path path = Paths.get(tempDirectoryPath + "/" + mazeName+".txt");
            //there is already this name in the folder
            if (Files.exists(path)) {
                File tmpdir = new File(tempDirectoryPath + "/" + mazeName+".txt");
                BufferedReader br = new BufferedReader(new FileReader(tempDirectoryPath + "/" + mazeName+".txt"));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                MyCompressorOutputStream outputStream = new MyCompressorOutputStream(byteArrayOutputStream);
                outputStream.write(maze.toByteArray());
                byte[] mymazebyte = byteArrayOutputStream.toByteArray();
                String mymaze = "";
                for (int i = 0; i < mymazebyte.length; i++)
                    mymaze = mymaze + "" + mymazebyte[i];
                String st;
                boolean exist = false;
                while ((st = br.readLine()) != null) {
                    String[] all = st.split(":");
                    String theMaze = all[0];
                    //the solution is already exists
                    if (theMaze.equals(mymaze)) {
                        exist = true;
                        solution = getSolutionFromTxt(all[1]);
                    }
                }
                //the file name exits but there is no equal maze
                if (exist == false) {
                    solution = WriteMazeAndSol(tempDirectoryPath + "/" + mazeName+".txt", maze);
                }
            } else { //we need to create a new file in the folder
                solution = WriteMazeAndSol(tempDirectoryPath + "/" + mazeName+".txt", maze);
            }
            toClient.writeObject(solution);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
