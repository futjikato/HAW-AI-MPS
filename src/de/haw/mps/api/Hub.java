package de.haw.mps.api;

import java.util.Observable;
import java.util.Observer;

public class Hub extends Observable {

    private static Hub instance;

    private Hub() { }

    public static Hub getInstance() {
        if(instance == null) {
            instance = new Hub();
        }
        return instance;
    }

    public void queueRequest(Request request) {
        setChanged();
        notifyObservers(request);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        if(!(o instanceof ActionController)) {
            throw new RuntimeException("Invalid Observer-type for Hub.");
        }
        super.addObserver(o);
    }
}
