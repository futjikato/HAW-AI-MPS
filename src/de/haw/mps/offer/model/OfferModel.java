package de.haw.mps.offer.model;

import de.haw.mps.offer.entity.CustomerEntity;
import de.haw.mps.offer.entity.OfferEntity;
import de.haw.mps.offer.entity.OrderEntity;
import de.haw.mps.persistence.AbstractModel;

public class OfferModel extends AbstractModel<OfferEntity> {

    public OfferModel() {
        super(OfferEntity.class);
    }

    public OfferEntity createOffer(CustomerEntity customer, OrderEntity order) {
        OfferEntity entity = new OfferEntity();
        entity.setCustomer(customer);
        entity.setResultingOrder(order);

        return entity;
    }

    public OfferEntity createOffer(CustomerEntity customer) {
        return createOffer(customer, null);
    }
}
