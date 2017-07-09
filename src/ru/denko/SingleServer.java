package ru.denko;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleServer implements Runnable {

    private final Socket incoming;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    SingleServer(Socket socket) {
        this.incoming = socket;
        logger.log(Level.INFO, "New client connected");
    }

    private InputStream input;
    private OutputStream output;

    @Override
    public void run() {
        try {
            input = incoming.getInputStream();
            output = incoming.getOutputStream();
            logger.log(Level.INFO, "get Input and Output stream");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread readSocket = new Thread(() -> {
            logger.log(Level.INFO, "Thread readSocket created");
            byte[] readBuf = new byte[1024];
            //InputStream in = new BufferedInputStream(input);
            while (true) {
                try {
                    input.read(readBuf);
                    logger.log(Level.INFO, "Server read from client:");
                    System.out.println(new String(readBuf));
                } catch (IOException e) {
                    System.out.println("Exception on readSocket thread");
                }
            }
        });
        readSocket.start();

        Thread consoleRead = new Thread(() -> {
            logger.log(Level.INFO, "Thread consoleRead created");
            byte[] consoleBuf = new byte[1024];
            while (true) {
                try {
                    InputStream console = new BufferedInputStream(System.in);
                    logger.log(Level.INFO, "InputStream from console created");
                    console.read(consoleBuf);
                    logger.log(Level.INFO, "Read from console");
                    output.write(consoleBuf);
                    logger.log(Level.INFO, "Write to client");
                } catch (IOException e) {
                    System.out.println("Exception in consoleRead thread");
                }
            }
        });
        consoleRead.start();

    }
}

