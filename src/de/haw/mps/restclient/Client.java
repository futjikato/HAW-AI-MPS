package de.haw.mps.restclient;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.restclient
 */
public class Client {

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final GsonFactory JSON_FACTORY = new GsonFactory();

    private HttpRequestFactory requestFactory;

    public Client() {
        requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) {
                request.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
    }

    public HttpRequest getRequest(GenericUrl url, String method) throws IOException {
        HttpRequest request = requestFactory.buildGetRequest(url);
        request.setRequestMethod(method);

        return request;
    }

    public HttpRequest post(GenericUrl url, HttpContent content) throws IOException {
        return requestFactory.buildPostRequest(url, content);
    }
}
