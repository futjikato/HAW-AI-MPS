package de.haw.mps.banking.messaging;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.*;
import de.haw.mps.api.network.client.Client;
import de.haw.mps.banking.entity.AccountMovementEntity;
import de.haw.mps.banking.model.AccountMovementModel;
import de.haw.mps.persistence.MpsSessionFactory;
import de.haw.mps.persistence.WorkflowException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.nio.ByteBuffer;
import java.util.logging.Level;

public final class BankMessage implements ExternalResponseProvider {

    private int amount;

    private String msg;

    private boolean succ;

    public BankMessage(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        parse(byteBuffer);
        succ = processs();
    }

    private boolean processs() {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();

        AccountMovementModel model = new AccountMovementModel();
        AccountMovementEntity entity = model.create(amount, msg);

        try {
            model.add(entity);
        } catch (WorkflowException e) {
            MpsLogger.getLogger().log(Level.SEVERE, "Unable to save account movement.", e);
            transaction.rollback();

            return false;
        }

        try {
            transaction.commit();
        } catch (Exception e) {
            MpsLogger.getLogger().log(Level.SEVERE, "Unable to commit account movement.", e);

            return false;
        }

        return true;
    }

    private void parse(ByteBuffer bb) {
        amount = bb.getInt();

        int msgStrLength = bb.getInt();
        byte[] msgBytes = new byte[msgStrLength];
        bb.get(msgBytes);
        msg = new String(msgBytes);
    }

    @Override
    public Response createResponse(Client client) {
        if(succ) {
            return new Response() {
                @Override
                public String getResponseName() {
                    return "account_change";
                }

                @Override
                public ResponseCode getResponseCode() {
                    return ResponseCode.OK;
                }

                @Override
                public String[] getData() {
                    return new String[]{String.valueOf(amount), msg};
                }
            };
        } else {
            return new Response() {
                @Override
                public String getResponseName() {
                    return "account_change";
                }

                @Override
                public ResponseCode getResponseCode() {
                    return ResponseCode.ERROR;
                }

                @Override
                public String[] getData() {
                    return new String[]{"Failed account movement."};
                }
            };
        }
    }
}
