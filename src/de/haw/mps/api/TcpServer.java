package de.haw.mps.api;

import de.haw.mps.MpsLogger;
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

    private static final int THREADPOOL_SIZE = 10;

    private ServerSocket serverSocket;

    private ExecutorService executor;

    public TcpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        executor = Executors.newFixedThreadPool(THREADPOOL_SIZE);
    }

    @Override
    public void run() {
        try {
            while(!isInterrupted()) {
                loop();
            }
        } catch (IOException e) {
            interrupt();
            MpsLogger.handleException(e);
        }
    }

    private void loop() throws IOException {
        final Socket clientSocket = serverSocket.accept();

        Future future = executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = RequestParser.parse(new Client(clientSocket), clientSocket.getInputStream());
                    Api api = Api.getInstance();
                    Response response = api.processRequest(request);
                    int bytesWrote = ResponseSerializer.write(clientSocket.getOutputStream(), response);

                    MpsLogger.getLogger().log(Level.INFO, String.format("Wrote response to client. Bytestreamsize: %d", bytesWrote));

                    clientSocket.close();
                } catch (IOException e) {
                    MpsLogger.getLogger().log(Level.SEVERE, "Dropping request because of read error. Exception follows.");
                    MpsLogger.handleException(e);
                } catch (ProcessException e) {
                    MpsLogger.getLogger().log(Level.SEVERE, "Dropping request because of processing error. Exception follows.");
                    MpsLogger.handleException(e);
                }
            }
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            MpsLogger.handleException(e);
        } catch (ExecutionException e) {
            MpsLogger.handleException(e);
        }
    }
}
