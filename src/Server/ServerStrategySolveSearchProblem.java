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
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            Solution solution=null;
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            Maze maze = (Maze)fromClient.readObject();
            String mazeName = ""+maze.getRows()+","+maze.getColumns()+","+maze.getStartPosition().toString()+maze.getGoalPosition().toString();
            Path path = Paths.get(tempDirectoryPath+"/"+mazeName);
            if(Files.exists(path)){
                File tmpdir = new File(tempDirectoryPath + "/" + mazeName);
                BufferedReader br = new BufferedReader(new FileReader(tmpdir));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ArrayList<AState> aStates = new ArrayList<>();
                String st;
                while((st = br.readLine())!=null){
                    String[] all = st.split(":");

                }
            }
            else{
                try {
                    File tmpdir = new File(tempDirectoryPath + "/" + mazeName);
                    FileOutputStream file = new FileOutputStream(tmpdir);
                    MyCompressorOutputStream comp = new MyCompressorOutputStream(file);
                    comp.write(maze.toByteArray());
                    FileWriter fileWriter = new FileWriter(tmpdir);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.print(":");
                    SearchableMaze domain = new SearchableMaze(maze);
                    BestFirstSearch searcher = new BestFirstSearch();
                    solution = searcher.solve(domain);
                    for (AState state:solution.getSolutionPath()) {
                        String[] s = state.getStateName().split(" ");
                        printWriter.print("("+s[0]+","+s[1]+")");
                    }
                    printWriter.println();
                }catch (IOException e){
                    System.out.println(e);
                }

            }

            toClient.writeObject(solution);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private AState createAState(String name, double cost){
        String[] sizes = name.split(" ");
        int x = Integer.parseInt(sizes[0]);
        int y = Integer.parseInt(sizes[1]);
        MazeState state = new MazeState(x,y,cost);
        return state;
    }
    public ServerStrategySolveSearchProblem() {
    }
}
