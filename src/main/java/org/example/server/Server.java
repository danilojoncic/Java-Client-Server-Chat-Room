package org.example.server;

import org.example.shared.Keeper;
import org.example.shared.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Keeper.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread cleaner = new Thread(new MessageCleaner());
        System.out.println("Listening on " + Keeper.address + " " + Keeper.port + " ...");
        cleaner.start();
        while(true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Thread serverThread = new Thread(new ServerThread(socket,this));
            serverThread.start();
            System.out.println("New client has connected!");
        }
    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendEveryone(Message message){
        String split[] = message.getContent().split(" ");
        StringBuilder censored = new StringBuilder();
        StringBuilder tmp = new StringBuilder();

        for (String s: split) {
            if (Keeper.bannedWords.contains(s)){
                int wordLength = s.length();
                tmp.append(s.charAt(0));
                tmp.append("*".repeat(Math.max(0, wordLength - 2)));
                tmp.append(s.charAt(wordLength - 1));
                censored.append(" ").append(tmp);
                tmp.setLength(0);
            }
            else censored.append(" ").append(s);
        }
        message.setContent(censored.toString());
        synchronized (Keeper.serverBlocker){
            Keeper.messages.add(message);
        }
        for (PrintWriter pw : Keeper.clientsWriters) {
            pw.println(message);
        }
    }
}
