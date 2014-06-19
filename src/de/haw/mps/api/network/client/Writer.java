package de.haw.mps.api.network.client;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.Request;
import de.haw.mps.api.Response;
import de.haw.mps.api.serialization.ResponseSerializer;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

public class Writer implements Runnable {

    private Client client;

    private final Queue<Request> queue = new ConcurrentLinkedQueue<Request>();

    public Writer(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Socket socket = this.client.getSocket();

        synchronized (queue) {
            while (!queue.isEmpty()) {
                Request request = queue.poll();

                if(request != null) {
                    Response response = request.getResponse();
                    if(response != null) {
                        try {
                            ResponseSerializer.write(socket.getOutputStream(), response, request);
                            MpsLogger.getLogger().log(Level.FINER, "wrote response to client :)");
                        } catch (IOException e) {
                            MpsLogger.getLogger().log(Level.SEVERE, "Unable to write response.", e);
                        }
                    }
                }
            }
        }
    }

    protected void queueAnsweredRequest(Request request) {
        queue.add(request);
        synchronized (queue) {
            queue.notify();
        }
    }
}
