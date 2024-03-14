package org.example.client;

import org.example.shared.Keeper;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String userName = null;

        try {
            socket = new Socket(Keeper.address, Keeper.port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            //korisnik se predstavlja ko je
            Scanner scanner = new Scanner(System.in);
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            userName = scanner.nextLine();

            //saljemo username
            out.println(userName);
            Thread t2 = new Thread(new ClientReaderThread(in));
            t2.start();
            while (true) {
                //klijent main thread samo salje poruke
                String message = scanner.nextLine();
                out.println(message);
                if(message.equalsIgnoreCase("exit")){
                    out.println(userName + " has exited the chat");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("The Server is unavailable!");
            //e.printStackTrace();
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

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
