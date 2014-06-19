package de.haw.mps.api.network;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.*;
import de.haw.mps.api.network.client.Client;
import de.haw.mps.api.serialization.RequestParser;
import de.haw.mps.api.serialization.ResponseSerializer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

public class TcpServer extends Thread {

    private ServerSocket serverSocket;

    public TcpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while(!isInterrupted()) {
                loop();
            }
        } catch (IOException e) {
            interrupt();
            MpsLogger.getLogger().severe(e.getMessage());
        }
    }

    private void loop() throws IOException {
        Socket clientSocket = serverSocket.accept();
        Client client = new Client(clientSocket);

        // start client
        client.start();

        // add client to external api manager
        ExternalApiManager.getInstance().addClient(client);
    }
}
