package org.example.server;

import org.example.shared.Keeper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(Keeper.port);
            while(true){
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected to " + socket.getInetAddress().toString());
                Thread thread = new Thread(new ServerThread(socket));
                thread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
