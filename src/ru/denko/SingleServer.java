package ru.denko;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleServer implements Runnable {

    private final Socket incoming;
    private volatile ClientList clientList;
    private InputStream input;
    private OutputStream output;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String clientName;
    private int closed;

    SingleServer(Socket socket, ClientList clientList) {
        this.incoming = socket;
        this.clientList = clientList;
        logger.log(Level.INFO, "New client connected");
        try {
            input = incoming.getInputStream();
            output = incoming.getOutputStream();
        } catch (IOException e) {
            e.getStackTrace();
        }
        logger.log(Level.INFO, "get Input and Output stream");
    }

    @Override
    public void run() {
        try {
            byte[] clientNameByByte = new byte[32];
            input.read(clientNameByByte);
            clientName = new String(clientNameByByte);
            clientList.addClient(clientName + new String(new byte[]{10}), incoming);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread readSocket = new Thread(() -> {
            logger.log(Level.INFO, "Thread readSocket created");
            //InputStream in = new BufferedInputStream(input);
            while (true) {
                try {
                    byte[] readBuf = new byte[1024];
                    byte[] to = new byte[128];

                    input.read(to);
                    System.out.println(new String(to));
                    closed = input.read(readBuf);
                    System.out.println(new String(readBuf));
                    String toClient = new String(to);
                    Socket toSocket = clientList.getClientSocket(toClient);
                    System.out.println(clientList.getClientSocket("Ivan"));
                    System.out.println("Ivan".equals(toClient));
                    for (byte b : "Ivan".getBytes()) {
                        System.out.print(b + " ");
                    }
                    System.out.println();
                    for (byte b : toClient.getBytes()) {
                        System.out.print(b + " ");
                    }
                    if (toSocket != null) {
                        System.out.println(toSocket);
                        OutputStream temp = toSocket.getOutputStream();
                        temp.write(readBuf);
                    } else {
                        System.out.println("Socket not found");
                    }

                    if (closed == -1) {
                        break;
                    }
                    logger.log(Level.INFO, "Server read from client:");
                    clientList.toString();
                    System.out.println(clientName + " to " + toClient + ": " + new String(readBuf));
                } catch (IOException e) {
                    System.out.println("Exception on readSocket thread");
                }
            }
        });
        readSocket.start();

        Thread consoleRead = new Thread(() -> {
            logger.log(Level.INFO, "Thread consoleRead created");
            while (true) {
                try {
                    byte[] consoleBuf = new byte[1024];
                    byte[] to = new byte[128];
                    InputStream console = new BufferedInputStream(System.in);
                    logger.log(Level.INFO, "InputStream from console created");
//                    console.read(to);
//                    output.write(to);
                    console.read(consoleBuf);
                    logger.log(Level.INFO, "Read from console");
                    output.write(consoleBuf);
                    logger.log(Level.INFO, "Write to client");

                } catch (IOException e) {
                    System.out.println("Exception in consoleRead thread");
                }
                if (closed == -1) {
                    break;
                }
            }
        });
        consoleRead.start();
    }
}

