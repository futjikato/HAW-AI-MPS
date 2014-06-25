package de.haw.mps.transport.api;

import com.google.api.client.http.HttpResponseException;
import de.haw.mps.MpsLogger;
import de.haw.mps.api.ActionController;
import de.haw.mps.api.Request;
import de.haw.mps.api.Response;
import de.haw.mps.api.ResponseCode;
import de.haw.mps.persistence.MpsSessionFactory;
import de.haw.mps.transport.UPPS;
import de.haw.mps.transport.UppsException;
import de.haw.mps.transport.entity.TransportEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.logging.Level;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.transport.api
 */
public class TransportController extends ActionController {

    public enum Actions {

        ADD_TRANSPORT{
            @Override
            public Response process(Request request) {
                Session session = MpsSessionFactory.getcurrentSession();
                Transaction transaction = session.beginTransaction();

                TransportEntity entity = new TransportEntity();

                String[] params = request.getParameters();

                if(params.length != 4) {
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{"Invalid amount of params"});
                }

                entity.setCountryCode(params[0]);
                entity.setPostal(params[1]);
                entity.setCity(params[2]);
                entity.setAddress(params[3]);
                entity.setSend(false);

                session.save(entity);
                session.refresh(entity);

                try {
                    TransportEntity remoteEntity = UPPS.send(entity);

                    entity.setRemoteId(remoteEntity.getId());

                    session.update(entity);
                    transaction.commit();
                } catch(UppsException e) {
                    MpsLogger.getLogger().log(Level.SEVERE, "Unable to send transport to UPPS", e);
                    transaction.rollback();
                }

                return ActionController.createResponse("transport_saved", ResponseCode.OK, new String[]{});
            }
        };

        public abstract Response process(Request request);
    }

    @Override
    public boolean liableForAction(String action) {
        try {
            return (Actions.valueOf(action) != null);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void process(Request request) {
        Actions action = Actions.valueOf(request.requestedAction());
        request.setResponse(action.process(request));
    }
}
