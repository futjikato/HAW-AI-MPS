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
                    public Client getSender() {
                        return request.getClient();
                    }

                    @Override
                    public Object getData() {
                        return "pong";
                    }
                };
            }
        };

        public abstract Response process(Request request);
    }

    @Override
    public boolean liableForAction(String action) {
        return (SystemActions.valueOf(action.toUpperCase()) != null);
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
