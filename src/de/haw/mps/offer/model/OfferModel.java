package de.haw.mps.offer.model;

import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.offer.entity.CustomerEntity;
import de.haw.mps.offer.entity.OfferEntity;
import de.haw.mps.offer.entity.OrderEntity;
import de.haw.mps.persistence.AbstractModel;
import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OfferModel extends AbstractModel<OfferEntity> {

    public OfferModel() {
        super(OfferEntity.class);
    }

    public OfferEntity createOffer(CustomerEntity customer, ElementEntity element, OrderEntity order) {
        OfferEntity entity = new OfferEntity();
        entity.setOrderObject(element);
        entity.setCustomer(customer);
        entity.setResultingOrder(order);

        return entity;
    }

    public OfferEntity createOffer(CustomerEntity customer, ElementEntity element) {
        return createOffer(customer, element, null);
    }

    public List getOffers() {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();

        Criteria crit = session.createCriteria(OfferEntity.class);
        List offers = crit.list();

        transaction.commit();

        return offers;
    }
}
