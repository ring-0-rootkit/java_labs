package org.labs.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    @Override
    public synchronized void run() {
        while (Main.isRunning) {
            try {
                var socket = Main.server.accept();

                Connections connections = new Connections(
                        socket,
                        new ObjectOutputStream(socket.getOutputStream()),
                        new ObjectInputStream(socket.getInputStream())
                );
                Main.connectionsMapping.put(
                        socket.getPort(),
                        connections
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}