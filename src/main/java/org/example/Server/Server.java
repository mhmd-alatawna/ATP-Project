package org.example.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    IServerStrategy strategy;
    int port ;
    int interval ;
    volatile boolean stop ;
    ExecutorService executor ;
    public Server(int port , int interval , IServerStrategy strategy) {
        this.strategy = strategy;
        this.port = port;
        this.interval = interval;
        this.stop = false;
        this.executor = Executors.newFixedThreadPool(Configurations.getInstance().getThreadPoolLimit());
    }

    public void start() {
        new Thread(() -> {
            try
                {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(interval);
                while (!stop) {
                    try {
                        Socket socket = serverSocket.accept();
                        executor.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    strategy.serverstrategy(socket.getInputStream(), socket.getOutputStream());
                                    socket.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (SocketTimeoutException e) {
                        System.out.println("Socket timed out");
                    }
                }
                serverSocket.close();
                executor.shutdownNow();
            } catch(
            Exception e)

            {
                System.out.println("an error occurred while accepting connection");
            }
        }).start();
    }

    public void stop(){
        stop = true ;
    }
}
