package de.haw.mps.api;

public abstract class Request {

    private Response response;

    public abstract Client getClient();

    public abstract String requestedAction();

    public abstract String[] getParameters();

    public void setResponse(Response response) {
        this.response = response;
        synchronized (this) {
            this.notify();
        }
    }

    public Response getResponse() {
        return response;
    }

}
