package ru.denko;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        //System.out.println("1" + new String(new byte[]{10}) + "1");
        SimpleServer simpleServer = new SimpleServer(18989);
        simpleServer.createServer();

    }
}
/**
 * TODO Закрытие основного треда только после закрытия сокета,
 * так чтобы работал трай с ресурсами
 * TODO Добавить client handler для отправки сообщений конкрентому клиенту
 * TODO Добавить client handler для отправки сообщений конкрентому клиенту
 */

/** TODO Добавить client handler для отправки сообщений конкрентому клиенту
 */