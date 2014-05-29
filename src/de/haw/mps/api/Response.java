package de.haw.mps.api;

public interface Response {

    public ResponseCode getResponseCode();

    public Client getSender();

    public String[] getData();

}
