package ru.denko;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientList {
    private Map<String, Socket> clients = new HashMap<>();

    void addClient(String name, Socket socket) {
        clients.put(name, socket);
    }

    Socket getClientSocket(String name) {
        return clients.get(name);
    }

    void removeClient(String name) {
        clients.remove(name);
    }

    Map map() {
        return clients;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (Map.Entry<String, Socket> entry : clients.entrySet()) {
            r.append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return new String(r);
    }
}
