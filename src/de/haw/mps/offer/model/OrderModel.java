package de.haw.mps.offer.model;

import de.haw.mps.offer.entity.OfferEntity;
import de.haw.mps.offer.entity.OrderEntity;
import de.haw.mps.persistence.AbstractModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class OrderModel extends AbstractModel<OrderEntity> {

    private static final int DEFAULT_INVOIDEDAYS = 35;
    private static final int DEFAULT_SHIPPINGDAYS = 20;

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

    public OrderEntity createOrder(Calendar orderDate, Calendar shippingDate, Calendar invoiceDate) {
        return createOrder(new HashSet<OfferEntity>(), orderDate, shippingDate, invoiceDate);
    }

    public OrderEntity createOrder(Calendar orderDate, Calendar shippingDate) {
        Calendar invoiceDate = new GregorianCalendar();
        invoiceDate.add(GregorianCalendar.DAY_OF_MONTH, DEFAULT_INVOIDEDAYS);

        return createOrder(orderDate, shippingDate, invoiceDate);
    }

    public OrderEntity createOrder(Calendar orderDate) {
        Calendar shippingDate = new GregorianCalendar();
        shippingDate.add(GregorianCalendar.DAY_OF_MONTH, DEFAULT_SHIPPINGDAYS);

        return createOrder(orderDate, shippingDate);
    }
}
