package de.haw.mps.banking.messaging;


import java.util.AbstractQueue;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageService extends Observable {

    private static MessageService instance;

    private AbstractQueue<BankMessage> queue;

    private MessageService() {
        queue = new ConcurrentLinkedQueue<BankMessage>();
    }

    public static MessageService getInstance() {
        if(instance == null) {
            instance = new MessageService();
        }

        return instance;
    }

    protected void add(BankMessage message) {
        queue.add(message);
        setChanged();
        notifyObservers();
    }

    public BankMessage popMesssage() {
        return queue.poll();
    }
}
