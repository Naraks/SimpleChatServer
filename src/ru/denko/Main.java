package ru.denko;

import java.io.IOException;

public class Main {
    /**
     * TODO Закрытие основного треда только после закрытия сокета, так чтобы работал трай с ресурсами
     * TODO Добавить client handler для отправки сообщений конкрентому клиенту
     */
    public static void main(String[] args) throws IOException {
        SimpleServer simpleServer = new SimpleServer(18989);
        simpleServer.createServer();
    }
}
