package de.haw.mps.transport;

import com.google.api.client.http.GenericUrl;
import de.haw.mps.restclient.Client;
import de.haw.mps.transport.entity.TransportEntity;

import java.io.IOException;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.transport
 */
public class UPPS {

    public static TransportEntity send(TransportEntity entity) {
        GenericUrl url = new GenericUrl("http://localhost:8080/mpstrans/transports");

        try {
            Client restclient = new Client();
            restclient.query(url, "POST");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
