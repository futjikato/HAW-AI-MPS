package de.haw.mps.banking.messaging;

import java.util.Observable;

public class MessageService extends Observable {

    private static MessageService instance;

    public static MessageService getInstance() {
        if(instance == null) {
            instance = new MessageService();
        }

        return instance;
    }

    protected void add(BankMessage message) {
        setChanged();
        notifyObservers(message);
    }
}
