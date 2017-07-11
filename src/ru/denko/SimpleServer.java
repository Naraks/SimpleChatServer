package ru.denko;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleServer {

    private final int port;
    private Logger logger = Logger.getLogger("SimpleServerLog");
    private boolean isWorking = true;

    SimpleServer(int port) {
        this.port = port;
    }

    public void createServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ClientList clientList = new ClientList();
        logger.log(Level.INFO, "Server created");
        logger.log(Level.INFO, "Server accept client");
        while (isWorking) {
            Socket incoming = serverSocket.accept();
            logger.log(Level.INFO, "Server accepted client");
            Runnable r = new SingleServer(incoming, clientList);
            Thread t = new Thread(r);
            t.start();
            logger.log(Level.INFO, "New server start");
            //clientList.toString();
        }
        logger.log(Level.INFO, "Server closed");
    }
}
