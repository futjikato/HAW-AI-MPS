package de.haw.mps.api.network.client;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.*;

import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

public class Client extends Thread {

    private Socket socket;

    private Reader reader;

    private Writer writer;

    private int requestCounter;

    public Client(Socket socket) {
        this.socket = socket;

        reader = new Reader(this);
        writer = new Writer(this);
    }

    public int getRequestCounter() {
        return requestCounter;
    }

    public void resetRequestCounter() {
        requestCounter = 0;
    }

    public void increaseRequestCounter() {
        requestCounter++;
    }

    protected Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {
            socket.setSoTimeout(500);
        } catch (SocketException e) {
            MpsLogger.getLogger().log(Level.SEVERE, "Unable to set socket timeout", e);
            interrupt();
        }

        while(!isInterrupted()) {
            reader.run();
            writer.run();
        }

        // remove from external api call manager
        ExternalApiManager.getInstance().removeClient(this);

        MpsLogger.getLogger().info("Exiting client");
    }

    public void handle(Request request) {
        increaseRequestCounter();

        if(request.getResponse() == null) {
            try {
                Api api = Api.getInstance();
                api.processRequest(request);
            } catch (ProcessException e) {
                MpsLogger.getLogger().log(Level.SEVERE, "Failed to process request.", e);
            }
        }

        writer.queueAnsweredRequest(request);
    }
}
