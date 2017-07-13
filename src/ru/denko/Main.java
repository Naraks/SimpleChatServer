package ru.denko;

import java.io.IOException;

/**
 * Simple echo chat console server
 * Supports sending messages to a specific client
 * Supports multiclients
 *
 * @author Rudenko Sergey
 * @author naraks@yandex.ru
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        SimpleServer simpleServer = new SimpleServer(18989);
        try {
            simpleServer.createServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
