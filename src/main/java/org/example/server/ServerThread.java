package org.example.server;

import org.example.shared.Keeper;
import org.example.shared.Message;

import java.io.*;
import java.net.Socket;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerThread implements Runnable{

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        String username = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.println("Welcome to the server...");
            out.println("Enter you name: ");
            username = in.readLine();
            out.println("Enjoy your stay " + username);


            while (true) {
                //prima poruku
                Message message = new Message(username,in.readLine(),new Date());
                //dodaje je na queue
                Keeper.messageQueue.add(message);
                //salje nazad
                out.println(Keeper.messageQueue.poll());
            }

        } catch (IOException e) {
            System.out.println("User: " + username + " has disconnected!");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                out.close();
            }

            if (this.socket != null) {
                   try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
