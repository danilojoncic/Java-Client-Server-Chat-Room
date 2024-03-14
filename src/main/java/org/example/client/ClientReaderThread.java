package org.example.client;

import org.example.shared.Keeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReaderThread implements Runnable{
    BufferedReader br = null;

    public ClientReaderThread(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
        try {
            while(true){
                //klijent reader thread samo cita poruke koje mu stizu sa servera
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
