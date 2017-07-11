package ru.denko;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientList {
    private Map<String, Socket> clients = new HashMap<>();

    public void addClient(String name, Socket socket) {
        clients.put(name, socket);
    }

    public Socket getClientSocket(String name) {
        return clients.get(name);
    }

    public void removeClient(String name) {
        clients.remove(name);
    }

    @Override
    public String toString() {
        for (Map.Entry<String, Socket> entry : clients.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        return "";
    }
}
