package de.haw.mps.offer.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@Entity(name = "Offer")
@Table(appliesTo = "Offer")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private Set<OfferEntity> offers;

    @Column
    private Calendar orderDate;

    @Column
    private Calendar shippingDate;

    @Column
    private Calendar invoiceDate;

    public Long getId() {
        return id;
    }

    public Set<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(Set<OfferEntity> offers) {
        this.offers = offers;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public Calendar getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Calendar shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Calendar getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Calendar invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
