package ru.denko;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        SimpleServer simpleServer = new SimpleServer(18989);
        simpleServer.createServer();
    }
}
