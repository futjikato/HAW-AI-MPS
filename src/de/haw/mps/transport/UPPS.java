package de.haw.mps.transport;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import de.haw.mps.MpsLogger;
import de.haw.mps.restclient.Client;
import de.haw.mps.transport.entity.TransportEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.*;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.transport
 */
public class UPPS {

    public static TransportEntity send(TransportEntity entity) throws UppsException {
        GenericUrl url = new GenericUrl("http://localhost:8080/mpstrans/transports");

        try {
            Client restclient = new Client();

            HttpContent content = new JsonHttpContent(new GsonFactory(), entity);
            HttpRequest request = restclient.post(url, content);

            try {
                return request.execute().parseAs(TransportEntity.class);
            } catch (HttpResponseException e) {
                MpsLogger.getLogger().log(Level.SEVERE, "Response Code not OK", e);
                MpsLogger.getLogger().info(String.format("Request Headers : %s", request.getHeaders().toString()));
                MpsLogger.getLogger().info(String.format("Response Headers : %s", request.getResponseHeaders().toString()));
                throw new UppsException(e);
            }
        } catch (IOException e) {
            throw new UppsException(e);
        }
    }

}
