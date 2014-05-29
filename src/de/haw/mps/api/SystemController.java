package de.haw.mps.api;


public final class SystemController extends ActionController {

    public enum SystemActions {
        PING() {
            @Override
            public Response process(final Request request) {
                return new Response() {
                    @Override
                    public ResponseCode getResponseCode() {
                        return ResponseCode.OK;
                    }

                    @Override
                    public String[] getData() {
                        return new String[] {"pong", String.valueOf(System.currentTimeMillis())};
                    }
                };
            }
        };

        public abstract Response process(Request request);
    }

    @Override
    public boolean liableForAction(String action) {
        try {
            return (SystemActions.valueOf(action.toUpperCase()) != null);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void process(Request request) {
        SystemActions action = SystemActions.valueOf(request.requestedAction());

        if(action == null) {
            throw new NullPointerException("Action liable but not found for processing.");
        }

        request.setResponse(action.process(request));
    }
}
