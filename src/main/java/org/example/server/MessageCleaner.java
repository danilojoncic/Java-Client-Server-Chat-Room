package org.example.server;

import org.example.server.Server;
import org.example.shared.Keeper;

public class MessageCleaner implements Runnable{

    @Override
    public void run() {
        while(true){
            if(Keeper.messages.size() > 100){
                synchronized (Keeper.serverBlocker){
                    Keeper.messages.remove(0);
                }
            }
        }
    }
}
