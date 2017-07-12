package ru.denko;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleServer implements Runnable {

    private final Socket socket;

    private ClientList clientList;
    private String clientName;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    SingleServer(Socket socket, ClientList clientList) {
        this.socket = socket;
        this.clientList = clientList;
        logger.log(Level.INFO, "New client connected");
    }

    @Override
    public void run() {
        try {
            clientName = readClient();
            clientList.addClient(clientName, socket);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                System.out.println("Could not close socket");
            }
        }

        readSocket();
        readConsole();
    }

    private void readConsole() {
        new Thread(() -> {
            logger.log(Level.INFO, "Thread readConsole created");
            BufferedReader fromConsole;
            String to;
            String msg;
            while (true) {
                try {
                    fromConsole = new BufferedReader(new InputStreamReader(System.in));
                    to = fromConsole.readLine();
                    msg = fromConsole.readLine();
                    writeToClient(to, msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        System.out.println("Could not close socket");
                    }
                    break;
                }
            }
        }).start();
    }

    private void readSocket() {
        new Thread(() -> {
            logger.log(Level.INFO, "Thread readSocket created");
            String to;
            String msg;
            while (true) {
                try {
                    to = readClient();
                    msg = readClient();
                    logger.log(Level.INFO, "Server read from client:");
                    System.out.println(to + ": " + msg); //убрать после тестов
                    writeToClient(to, clientName + ": " + msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        System.out.println("Could not close socket");
                    }
                    break;
                }
            }
        }).start();
    }

    private String readClient() throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        return in.readUTF();
    }

    private void writeToClient(String to, String msg) throws IOException {
        logger.log(Level.INFO, "Sending message to " + to);
        if (to.equals("")) {
            for (Object key : clientList.map().keySet()) {
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientList.getClientSocket((String) key).getOutputStream()));
                out.writeUTF(msg);
                out.flush();
            }
        } else {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(clientList.getClientSocket(to).getOutputStream()));
            out.writeUTF(msg);
            out.flush();
        }
    }
}

