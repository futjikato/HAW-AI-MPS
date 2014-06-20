package de.haw.mps.api;

import de.haw.mps.api.network.client.Client;

public interface ExternalResponseProvider {

    public Response createResponse(Client client);

}
