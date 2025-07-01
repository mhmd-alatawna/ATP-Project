package org.example.Client;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
    InetAddress serverIP;
    int serverPort;
    IClientStrategy strategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    public void communicateWithServer(){
        Socket socket ;
        try {
            socket = new Socket(serverIP, serverPort);
            strategy.clientStrategy(socket.getInputStream(), socket.getOutputStream());
            socket.close();
        }catch (Exception e){
            System.out.println("connection to server failed");
        }
    }
}
