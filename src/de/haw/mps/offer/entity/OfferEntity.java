package de.haw.mps.offer.entity;

import de.haw.mps.fabrication.entity.ElementEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Offer")
@Table(appliesTo = "Offer")
public class OfferEntity implements Serializable {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private CustomerEntity customer;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private OrderEntity resultingOrder;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ElementEntity orderObject;

    public Long getId() {
        return id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public OrderEntity getResultingOrder() {
        return resultingOrder;
    }

    public void setResultingOrder(OrderEntity resultingOffer) {
        this.resultingOrder = resultingOffer;
    }

    public ElementEntity getOrderObject() {
        return orderObject;
    }

    public void setOrderObject(ElementEntity orderObject) {
        this.orderObject = orderObject;
    }
}
