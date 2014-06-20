package de.haw.mps.api;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.network.client.Client;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ExternalApiManager implements Observer {

    private List<Client> clientList = new CopyOnWriteArrayList<Client>();

    private static ExternalApiManager instance;

    public static ExternalApiManager getInstance() {
        if(instance == null) {
            instance = new ExternalApiManager();
        }

        return instance;
    }

    private ExternalApiManager() { }

    public void addClient(Client client) {
        clientList.add(client);
    }

    public void removeClient(Client client) {
        clientList.remove(client);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ExternalRequestProvider) {
            MpsLogger.getLogger().info("Processing external request");
            for(Client client : clientList) {
                ExternalRequestProvider requestProvider = (ExternalRequestProvider) arg;
                client.handle(requestProvider.createRequest(client));
            }

            return;
        }

        if(o instanceof ExternalResponseProvider) {
            MpsLogger.getLogger().info("Processing external response");
            for(Client client : clientList) {
                ExternalResponseProvider responseProvider = (ExternalResponseProvider) arg;
                Request request = getMockRequest(client);
                request.setResponse(responseProvider.createResponse(client));

                client.handle(request);
            }

            return;
        }

        MpsLogger.getLogger().info("External api update ignored");
    }

    private Request getMockRequest(final Client client) {
        return new Request() {
            @Override
            public Client getClient() {
                return client;
            }

            @Override
            public String requestedAction() {
                return null;
            }

            @Override
            public String[] getParameters() {
                return new String[0];
            }

            @Override
            public int getUserId() {
                return 0;
            }
        };
    }
}
