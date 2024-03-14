package org.example.client;

import org.example.shared.Message;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ClientSender implements Runnable{

    Socket socket;
    Client client;

    public ClientSender(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            PrintWriter outSocket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner scanner = new Scanner(System.in);
            while(true){
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("exit")){
                    outSocket.println("exit");
                    break;
                }
                outSocket.println(input);
            }
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
