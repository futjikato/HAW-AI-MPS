package de.haw.mps.api;

import java.net.Socket;

public class Client {

    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

}
