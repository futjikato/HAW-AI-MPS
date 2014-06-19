package de.haw.mps.api.network.client;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.Request;
import de.haw.mps.api.serialization.RequestParser;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;

public class Reader implements Runnable {

    private Client client;

    public Reader(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Socket socket = this.client.getSocket();
        try {
            Request request = RequestParser.parse(client, socket.getInputStream());
            client.handle(request);
        } catch (SocketTimeoutException e) {
            // Timeout. Simply time to check if we need to write something. nothing to worry about.
        } catch (IOException e) {
            MpsLogger.getLogger().log(Level.SEVERE, "Failed to set up input stream reading.", e);
            Thread.currentThread().interrupt();
        }
    }
}
