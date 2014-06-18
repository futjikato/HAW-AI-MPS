package de.haw.mps.offer.model;

import de.haw.mps.offer.entity.OfferEntity;
import de.haw.mps.offer.entity.OrderEntity;
import de.haw.mps.persistence.AbstractModel;
import de.haw.mps.persistence.MpsSessionFactory;
import de.haw.mps.persistence.WorkflowException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class OrderModel extends AbstractModel<OrderEntity> {

    private static final int DEFAULT_INVOIDEDAYS = 35;
    private static final int DEFAULT_SHIPPINGDAYS = 20;

    public OrderModel() {
        super(OrderEntity.class);
    }

    public OrderEntity createOrder(Set<OfferEntity> offers, Calendar orderDate, Calendar shippingDate, Calendar invoiceDate) {
        OrderEntity entity = new OrderEntity();
        entity.setOffers(offers);
        entity.setOrderDate(orderDate);
        entity.setShippingDate(shippingDate);
        entity.setInvoiceDate(invoiceDate);

        return entity;
    }

    public OrderEntity createOrder(OfferEntity offer, Calendar orderDate, Calendar shippingDate, Calendar invoiceDate) {
        Set<OfferEntity> offers = new HashSet<OfferEntity>();

        if(offer != null) {
            offers.add(offer);
        }

        return createOrder(offers, orderDate, shippingDate, invoiceDate);
    }

    public OrderEntity createOrder(OfferEntity offer, Calendar orderDate, Calendar shippingDate) {
        Calendar invoiceDate = new GregorianCalendar();
        invoiceDate.add(GregorianCalendar.DAY_OF_MONTH, DEFAULT_INVOIDEDAYS);

        return createOrder(offer, orderDate, shippingDate, invoiceDate);
    }

    public OrderEntity createOrder(OfferEntity offer, Calendar orderDate) {
        Calendar shippingDate = new GregorianCalendar();
        shippingDate.add(GregorianCalendar.DAY_OF_MONTH, DEFAULT_SHIPPINGDAYS);

        return createOrder(offer, orderDate, shippingDate);
    }

    public List getOrders() throws WorkflowException {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.getTransaction();
        if(transaction == null || !transaction.isActive()) {
            throw new WorkflowException("No transaction running while trying to get object.");
        }

        Criteria crit = session.createCriteria(OrderEntity.class);
        return crit.list();
    }
}
