package org.example.client;

import org.example.shared.Keeper;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String username;
    public static Socket socket = null;
    public static BufferedReader in = null;
    public static PrintWriter out = null;

    public Client() throws IOException, InterruptedException {
        socket = new Socket(Keeper.address,Keeper.port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        Scanner scanner = new Scanner(System.in);

        //odgovor serveru na pitanje ko je korisnik
        System.out.println(in.readLine());
        username = scanner.nextLine();
        out.println(username);


        String response = in.readLine();
        System.out.println(response);
        if(response.equalsIgnoreCase("User with that name already exists")){
            return;
        }

        Thread readThread = new Thread(new ClientReciever(socket,this));
        Thread sendThread = new Thread(new ClientSender(socket,this));
        readThread.start();
        sendThread.start();
        sendThread.join();
        readThread.join();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
