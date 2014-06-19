package de.haw.mps.api;

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
        if(arg == null) {
            return;
        }

        if(o instanceof ExternalRequestProvider) {
            for(Client client : clientList) {
                ExternalRequestProvider requestProvider = (ExternalRequestProvider) arg;
                client.handle(requestProvider.createRequest(client));
            }
        }
    }
}
