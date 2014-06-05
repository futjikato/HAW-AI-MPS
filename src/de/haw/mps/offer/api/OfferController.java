package de.haw.mps.offer.api;

import de.haw.mps.MpsLogger;
import de.haw.mps.api.ActionController;
import de.haw.mps.api.Request;
import de.haw.mps.api.Response;
import de.haw.mps.api.ResponseCode;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.fabrication.model.ElementModel;
import de.haw.mps.offer.entity.CustomerEntity;
import de.haw.mps.offer.entity.OfferEntity;
import de.haw.mps.offer.entity.OrderEntity;
import de.haw.mps.offer.model.CustomerModel;
import de.haw.mps.offer.model.OfferModel;
import de.haw.mps.offer.model.OrderModel;
import de.haw.mps.persistence.MpsSessionFactory;
import de.haw.mps.persistence.WorkflowException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.GregorianCalendar;
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
        },

        NEW_OFFER() {
            @Override
            public Response process(Request request) {
                String[] params = request.getParameters();

                if(params.length != 2) {
                    return ActionController.createResponse(ResponseCode.BADREQUEST, new String[]{"Exactly two parameter are expected."});
                }

                String customerName = params[0];
                String elementName = params[1];

                try {
                    // get model
                    CustomerModel customerModel = new CustomerModel();
                    ElementModel elementModel = new ElementModel();
                    OfferModel offerModel = new OfferModel();

                    // load objects
                    CustomerEntity customerEntity = customerModel.getCustomerByName(customerName);
                    ElementEntity elementEntity = elementModel.getElementByName(elementName);

                    // create new offer
                    OfferEntity newOffer = offerModel.createOffer(customerEntity, elementEntity);

                    // persist entity
                    Session session = MpsSessionFactory.getcurrentSession();
                    Transaction transaction = session.beginTransaction();
                    offerModel.add(newOffer);
                    transaction.commit();

                    // send response
                    String[] responseParams = new String[]{
                        String.valueOf(newOffer.getId()),
                        newOffer.getCustomer().getName(),
                        newOffer.getOrderObject().getName()
                    };
                    return ActionController.createResponse("new_offer", ResponseCode.OK, responseParams);
                } catch (Exception e) {
                    MpsLogger.getLogger().severe(e.getMessage());
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{"Unable to load customers."});
                }
            }
        },

        OFFER_TO_ORDER() {
            @Override
            public Response process(Request request) {
                String[] params = request.getParameters();

                // get id from params
                if(params.length != 1) {
                    return ActionController.createResponse(ResponseCode.BADREQUEST, new String[]{"Exactly one parameter must be given."});
                }
                Long id = Long.valueOf(params[0]);

                OfferModel model = new OfferModel();
                Session session = MpsSessionFactory.getcurrentSession();
                Transaction transaction = session.beginTransaction();

                // load offer
                OfferEntity offerEntity;
                try {
                    offerEntity = model.get(id);
                } catch (WorkflowException e) {
                    return ActionController.createResponse(ResponseCode.NOTFOUND, new String[]{"Offer not found."});
                }

                // check if already ordered
                if(offerEntity.getResultingOrder() != null) {
                    return ActionController.createResponse(ResponseCode.ALREADYDONE, new String[]{"Offer has already been ordered."});
                }

                OrderModel orderModel = new OrderModel();
                OrderEntity orderEntity = orderModel.createOrder(offerEntity, new GregorianCalendar());
                try {
                    orderModel.add(orderEntity);
                } catch (WorkflowException e) {
                    MpsLogger.getLogger().severe(e.getMessage());
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{"Unable to save order."});
                }

                try {
                    transaction.commit();
                } catch(Exception e) {
                    MpsLogger.getLogger().severe(e.getMessage());
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{"Unable to commit transaction."});
                }

                return ActionController.createResponse("new_order", ResponseCode.OK, new String[]{
                    String.valueOf(orderEntity.getId()),
                    String.valueOf(offerEntity.getId())
                });
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
