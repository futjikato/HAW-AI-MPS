package de.haw.mps.api;

import java.net.Socket;

public class Client {

    private Socket socket;
    private int requestCounter;

    public Client(Socket socket) {
        this.socket = socket;
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
}
