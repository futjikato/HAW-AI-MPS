package de.haw.mps.api.serialization;

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

    private ResponseSerializer(DataOutputStream dos, Response response) {
        responseCode = response.getResponseCode();
        data = response.getData();
        responseName = response.getResponseName();
        this.dos = dos;
    }

    public static int write(OutputStream os, Response response) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        ResponseSerializer serializer = new ResponseSerializer(dos, response);
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

        // write parameters
        dos.writeInt(data.length);
        for(String param : data) {
            dos.writeInt(param.length());
            dos.write(param.getBytes());
        }

        // flush to client
        dos.flush();

        return dos.size();
    }

}
