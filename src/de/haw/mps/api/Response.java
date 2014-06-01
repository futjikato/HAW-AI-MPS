package de.haw.mps.api;

public interface Response {

    public String getResponseName();

    public ResponseCode getResponseCode();

    public String[] getData();

}
