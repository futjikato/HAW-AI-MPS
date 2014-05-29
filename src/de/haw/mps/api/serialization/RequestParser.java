package de.haw.mps.api.serialization;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.Client;
import de.haw.mps.api.Request;

import java.io.*;
import java.util.logging.Level;

public class RequestParser {

    private DataInputStream reader;

    private String actionName;

    private String[] parameters;

    private RequestParser(DataInputStream reader) {
        this.reader = reader;
    }

    private void readData() throws IOException {
        actionName = readStr();
        MpsLogger.getLogger().log(Level.INFO, String.format("[REQUEST] - Action %s", actionName));

        int parameterCount = reader.readInt();
        parameters = readParameter(parameterCount);
    }

    private String[] readParameter(int amount) throws IOException {
        String[] parameters = new String[amount];

        for(int i = 0 ; i < amount ; i++) {
            parameters[i] = readStr();
        }

        return parameters;
    }

    private String readStr() throws IOException {
        int stringLength = reader.readInt();
        MpsLogger.getLogger().log(Level.INFO, String.format("[REQUEST] - String length %d", stringLength));
        byte[] strBytes = new byte[stringLength];
        reader.read(strBytes);
        return new String(strBytes);
    }

    public static Request parse(final Client client, InputStream input) throws IOException {
        DataInputStream reader = new DataInputStream(new BufferedInputStream(input));
        final RequestParser parser = new RequestParser(reader);
        parser.readData();

        return new Request() {
            @Override
            public Client getClient() {
                return client;
            }

            @Override
            public String requestedAction() {
                return parser.actionName;
            }

            @Override
            public String[] getParameters() {
                return parser.parameters;
            }
        };
    }

}
