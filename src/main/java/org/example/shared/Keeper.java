package org.example.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Keeper {
    /*
    jako bitna napomena jeste da u pravoj primjeni ja bi imao 2 aplikacije
    jedna je client i jedna server pri cemu ovaj shared paket bi zapravo obe te
    aplikacije imale zasebno u sebi, zasada je cisto radi lakseg pokretanja postavljeno ovako
     */
    public static final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    public static final HashMap<String,Boolean> nameInUse = new HashMap<>();
    public static final int port = 8080;
    public static final String address = "127.0.0.1";
    public static final List<String> bannedWords = new ArrayList<>(List.of(
            "losa_rec","losaRijec","psovka"));
}
