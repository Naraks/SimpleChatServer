package ru.denko;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create for each new connected client own server
 *
 * @throws IOException
 */
class SimpleServer {

    private final int port;
    private Logger logger = Logger.getLogger("SimpleServerLog");

    SimpleServer(int port) {
        this.port = port;
    }

    void createServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ClientList clientList = new ClientList();
        logger.log(Level.INFO, "Server created");

        while (true) {
            Socket incoming = serverSocket.accept();
            new Thread(new SingleServer(incoming, clientList)).start();
        }
        //logger.log(Level.INFO, "Server closed");
    }
}
