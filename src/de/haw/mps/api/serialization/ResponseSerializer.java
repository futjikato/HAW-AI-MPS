package de.haw.mps.api.serialization;

import de.haw.mps.api.Request;
import de.haw.mps.api.Response;
import de.haw.mps.api.ResponseCode;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseSerializer {

    private ResponseCode responseCode;

    private String[] data;

    private DataOutputStream dos;

    private String responseName;

    private int userIdentification;

    private ResponseSerializer(DataOutputStream dos, Response response, Request request) {
        responseCode = response.getResponseCode();
        data = response.getData();
        responseName = response.getResponseName();
        userIdentification = request.getUserId();
        this.dos = dos;
    }

    public static int write(OutputStream os, Response response, Request request) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        ResponseSerializer serializer = new ResponseSerializer(dos, response, request);
        return serializer.write();
    }

    private int write() throws IOException {
        // write response code
        dos.writeInt(responseCode.getCode());

        // write a string response name if exists
        if(responseName == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(responseName.length());
            dos.write(responseName.getBytes());
        }

        // write user id
        dos.writeInt(userIdentification);

        // write parameters count
        dos.writeInt(data.length);
        // write all parameters
        for(String param : data) {
            dos.writeInt(param.length());
            dos.write(param.getBytes());
        }

        // flush to client
        dos.flush();

        return dos.size();
    }

}
