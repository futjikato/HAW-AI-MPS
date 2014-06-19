package de.haw.mps.banking.messaging;

import de.haw.mps.api.network.client.Client;
import de.haw.mps.api.ExternalRequestProvider;
import de.haw.mps.api.Request;

public final class BankMessage implements ExternalRequestProvider {

    public BankMessage(byte[] bytes) {
        parse(bytes);
    }

    private void parse(byte[] bytes) {

    }

    @Override
    public Request createRequest(final Client client) {
        return new Request() {
            @Override
            public Client getClient() {
                return client;
            }

            @Override
            public String requestedAction() {
                return "";
            }

            @Override
            public String[] getParameters() {
                return new String[0];
            }

            @Override
            public int getUserId() {
                return 0;
            }
        };
    }
}
