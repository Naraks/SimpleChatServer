package ru.denko;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleServer {

    private Logger logger = Logger.getLogger("SimpleServerLog");

    private final int port;
    private boolean isWorking = true;

    SimpleServer(int port) {
        this.port = port;
    }

    public void createServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        logger.log(Level.INFO, "Server created");
        logger.log(Level.INFO, "Server accept client");
        while (isWorking) {
            Socket incoming = serverSocket.accept();
            logger.log(Level.INFO, "Server accepted client");
            Runnable r = new SingleServer(incoming);
            Thread t = new Thread(r);
            t.start();
            logger.log(Level.INFO, "New server start");
        }
        logger.log(Level.INFO, "Server closed");
    }
}
