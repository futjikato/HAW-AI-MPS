package de.haw.mps.api.serialization;

import de.haw.mps.api.Response;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseSerializer {

    private int responseCode;

    private String[] data;

    private DataOutputStream dos;

    private ResponseSerializer(DataOutputStream dos, Response response) {
        responseCode = response.getResponseCode().getCode();
        data = response.getData();
        this.dos = dos;
    }

    public static int write(OutputStream os, Response response) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        ResponseSerializer serializer = new ResponseSerializer(dos, response);
        return serializer.write();
    }

    private int write() throws IOException {
        // write response code
        dos.writeInt(responseCode);

        dos.writeInt(data.length);
        for(String param : data) {
            dos.writeInt(param.length());
            dos.write(param.getBytes());
        }

        dos.flush();

        return dos.size();
    }

}
