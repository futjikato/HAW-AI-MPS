package de.haw.mps.fabrication.api;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.*;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.fabrication.model.ElementModel;
import de.haw.mps.persistence.WorkflowException;

public class FabricationController extends ActionController {

    public enum Actions {
        ADD_ELEMENT() {
            @Override
            public Response process(final Request request) {
                String[] params = request.getParameters();

                if(params.length != 1) {
                    return ActionController.createResponse(ResponseCode.BADREQUEST, new String[]{"Missing or too many parameters."});
                }

                ElementModel elementModel = new ElementModel();
                final ElementEntity entity = elementModel.createElement(params[0]);

                boolean ok = false;
                try {
                    elementModel.startTransaction();
                    ok = elementModel.add(entity);
                    elementModel.commitTransaction();
                } catch (WorkflowException e) {
                    MpsLogger.getLogger().severe(e.getMessage());
                }

                if(!ok) {
                    return ActionController.createResponse(ResponseCode.BADREQUEST, new String[]{"Unable to save element."});
                }

                return ActionController.createResponse(ResponseCode.OK, new String[] {String.valueOf(entity.getId())});
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
