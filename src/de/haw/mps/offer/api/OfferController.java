package de.haw.mps.offer.api;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.ActionController;
import de.haw.mps.api.Request;
import de.haw.mps.api.Response;
import de.haw.mps.api.ResponseCode;
import de.haw.mps.offer.entity.CustomerEntity;
import de.haw.mps.offer.entity.OfferEntity;
import de.haw.mps.offer.entity.OrderEntity;
import de.haw.mps.offer.model.CustomerModel;
import de.haw.mps.offer.model.OfferModel;
import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OfferController extends ActionController {

    public enum Actions {
        GET_CUSTOMERS() {
            @Override
            public Response process(Request request) {
                try {
                    CustomerModel model = new CustomerModel();
                    List customers = model.getCustomers();

                    String[] params = new String[customers.size() * 2];
                    int i = 0;
                    for(Object entity : customers) {
                        if(entity instanceof CustomerEntity) {
                            CustomerEntity customerEntity = (CustomerEntity)entity;
                            params[i++] = String.valueOf(customerEntity.getId());
                            params[i++] = customerEntity.getName();
                        }
                    }

                    return ActionController.createResponse("customers", ResponseCode.OK, params);
                } catch (Exception e) {
                    MpsLogger.getLogger().severe(e.getMessage());
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{"Unable to load customers."});
                }
            }
        },

        NEW_CUSTOMER() {
            @Override
            public Response process(Request request) {
                String[] params = request.getParameters();

                if(params.length != 1) {
                    return ActionController.createResponse(ResponseCode.BADREQUEST, new String[]{"Exactly one parameter is expected."});
                }

                String name = params[0];

                try {
                    // get model
                    CustomerModel model = new CustomerModel();

                    // create new entity
                    CustomerEntity newCustomer = model.createCustomer(name);

                    // persist entity
                    Session session = MpsSessionFactory.getcurrentSession();
                    Transaction transaction = session.beginTransaction();
                    model.add(newCustomer);
                    transaction.commit();

                    // send response
                    String[] responseParams = new String[]{String.valueOf(newCustomer.getId()), newCustomer.getName()};
                    return ActionController.createResponse("new_customer", ResponseCode.OK, responseParams);
                } catch (Exception e) {
                    MpsLogger.getLogger().severe(e.getMessage());
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{"Unable to load customers."});
                }
            }
        },

        GET_OFFERS() {
            @Override
            public Response process(Request request) {
                try {
                    OfferModel model = new OfferModel();
                    List customers = model.getOffers();

                    String[] params = new String[customers.size() * 4];
                    int i = 0;
                    for(Object entity : customers) {
                        if(entity instanceof OfferEntity) {
                            OfferEntity offerEntity = (OfferEntity)entity;
                            params[i++] = String.valueOf(offerEntity.getId());
                            params[i++] = offerEntity.getCustomer().getName();
                            params[i++] = offerEntity.getOrderObject().getName();

                            OrderEntity order = offerEntity.getResultingOrder();
                            if(order == null) {
                                params[i++] = "-1";
                            } else {
                                params[i++] = String.valueOf(order.getId());
                            }
                        }
                    }

                    return ActionController.createResponse("offers", ResponseCode.OK, params);
                } catch (Exception e) {
                    MpsLogger.getLogger().severe(e.getMessage());
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{"Unable to load offers."});
                }
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
