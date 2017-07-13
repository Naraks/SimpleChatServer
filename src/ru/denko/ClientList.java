package ru.denko;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Storage for clients
 */
public class ClientList {
    private Map<String, Socket> clients = new HashMap<>();

    /**
     * Adding new connected client to storage
     * @param name client's name
     * @param socket client's socket
     */
    void addClient(String name, Socket socket) {
        clients.put(name, socket);
    }

    /**
     * @param name client's name
     * @return client's socket by client's name
     */
    Socket getClientSocket(String name) {
        return clients.get(name);
    }

    /**
     * Remove client from storage
     * @param name client's name
     */
    void removeClient(String name) {
        clients.remove(name);
    }

    /**
     * @return storage of clients
     */
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
