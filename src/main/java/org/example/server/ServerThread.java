package org.example.server;

import org.example.shared.Keeper;
import org.example.shared.Message;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerThread implements Runnable{
    private  final Socket socket;
    private  final Server server;
    PrintWriter out;
    BufferedReader in;
    String username;
    String inMsg;
    String outMsg;

    public ServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            out.println("Enter your name: ");
            username = in.readLine();

            if(Keeper.namesInUse.contains(username)){
                out.println("User with that name already exists");
                socket.close();
                return;
            }else{
                Keeper.namesInUse.add(username);
                out.println("Welcome user : " + username);
            }
            //stampanje istorije poruka iz liste poruka
            //koristeci bloker objekat radi izbbjegavanja concurent operation nad listom
            synchronized (Keeper.serverBlocker) {
                for (Message msg : Keeper.messages){
                    out.println(msg);
                }
            }

            //dodajem svoj stampac da je dostupa na sva slanja sa glavne liste poruka
            synchronized (Keeper.writersBlocker){
                Keeper.clientsWriters.add(out);
            }
            server.sendEveryone(new Message("server",username + " has joined the chat",new Date()));



            while(true){
                Message message = new Message(username,in.readLine(),new Date());
                if(message.getContent().equalsIgnoreCase("exit")){
                    synchronized (Keeper.nameBlocker){
                        Keeper.namesInUse.remove(username);
                    }
                    server.sendEveryone(new Message("server",username + " has left the chat!",new Date()));
                    break;
                }
                server.sendEveryone(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
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
