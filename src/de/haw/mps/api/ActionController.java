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

    public static Response createResponse(final String responseAction, final ResponseCode responseCode, final String[] data) {
        return new Response() {
            @Override
            public String getResponseName() {
                return responseAction;
            }

            @Override
            public ResponseCode getResponseCode() {
                return responseCode;
            }

            @Override
            public String[] getData() {
                return data;
            }
        };
    }

    public static Response createResponse(final ResponseCode responseCode, final String[] data) {
        return createResponse(null, responseCode, data);
    }

    public abstract boolean liableForAction(String action);

    public abstract void process(Request request);
}
