package de.haw.mps.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ExternalApiManager implements Observer{

    private List<Client> clientList;

    private Lock lock;

    public ExternalApiManager() {
        lock = new ReentrantLock();
        clientList = new ArrayList<Client>();
    }

    public void addClient(Client client) {
        lock.lock();
        clientList.add(client);
        lock.unlock();
    }

    public void removeClient(Client client) {
        lock.lock();
        clientList.remove(client);
        lock.unlock();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == null) {
            return;
        }

        if(o instanceof ExternalRequestProvider) {
            ExternalRequestProvider requestProvider = (ExternalRequestProvider) arg;
            publish(requestProvider);
        }
    }

    private void publish(ExternalRequestProvider requestProvider) {
        for(Client client : clientList) {
            Request request = requestProvider.createRequest(client);
        }
    }

    private void publish(Response response) {

    }
}
