package de.haw.mps.api;

import de.haw.mps.api.network.client.Client;

public interface ExternalRequestProvider {

    public Request createRequest(Client client);

}
