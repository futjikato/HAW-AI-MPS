package de.haw.mps.api;


import de.haw.mps.MpsLogger;

public final class Api {

    private Hub actionHub;

    private static Api instance;

    private Api() {
        // get Hub singleton instance
        actionHub = Hub.getInstance();
        // add system action controller
        SystemController systemController = new SystemController();
        actionHub.addObserver(systemController);
    }

    public Api getInstance() {
        if(instance == null) {
            instance = new Api();
        }

        return instance;
    }

    public Response processRequest(final Request request) throws ProcessException {
        Api.this.actionHub.queueRequest(request);

        synchronized (request) {
            try {
                request.wait();
            } catch (InterruptedException e) {
                // write exception into log file
                MpsLogger.handleException(e);
                // return error response
                return new Response() {
                    @Override
                    public ResponseCode getResponseCode() {
                        return ResponseCode.ERROR;
                    }

                    @Override
                    public Client getSender() {
                        return request.getClient();
                    }

                    @Override
                    public Object getData() {
                        return null;
                    }
                };
            }

            return request.getResponse();
        }
    }
}
