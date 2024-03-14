package org.example.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReciever implements Runnable{
    Socket socket;
    Client client;

    public ClientReciever(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            while(true){
                System.out.println(in.readLine());
            }
        }catch (Exception e){
            System.out.println("Leaving chat!");
        }

    }
}
