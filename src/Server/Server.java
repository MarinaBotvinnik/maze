package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        this.stop = false;
    }

    public void start() {
        new Thread(() -> {
            runServer();}).start();
    }

    private void runServer() {
        try {
            int ThreadSize= Integer.parseInt(Configurations.getProperty("Server.NumThread"));
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            ExecutorService executorService = Executors.newFixedThreadPool(6);
            System.out.println((String.format("Server starter at %s!", serverSocket)));
            System.out.println((String.format("Server's Strategy: %s", serverStrategy.getClass().getSimpleName())));
            System.out.println(("Server is waiting for clients..."));
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    System.out.println(String.format("Client excepted: %s", clientSocket));
                    executorService.execute(() -> {
                            handleClient(clientSocket);
                            System.out.println(String.format("Finished handle client: %s", clientSocket));
                    });
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket Timeout - No clients pending!");
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("IOException"+ e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            System.out.println(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException"+ e);
        }
    }

    public void stop() {
        System.out.println("Stopping server..");
        stop = true;
    }
}
