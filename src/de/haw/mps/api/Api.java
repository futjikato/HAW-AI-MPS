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

    public static Api getInstance() {
        if(instance == null) {
            instance = new Api();
        }

        return instance;
    }

    public Response processRequest(final Request request) throws ProcessException {
        actionHub.queueRequest(request);

        return request.getResponse();
    }
}
