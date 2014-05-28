package de.haw.mps.api;

import java.util.Observable;
import java.util.Observer;

public abstract class ActionController implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Hub && arg instanceof Request) {
            Request request = (Request) arg;
            if(liableForAction(request.requestedAction())) {
                process(request);
            }
        }
    }

    public abstract boolean liableForAction(String action);

    public abstract void process(Request request);
}
