package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
    public static final int port = 8080;
    public static final String address = "127.0.0.1";
    public static final List<String> bannedWords = new ArrayList<>(List.of(
            "bad_word1","bad_word2","bad_word3"));
}
