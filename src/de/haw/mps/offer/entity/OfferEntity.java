package de.haw.mps.offer.entity;

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

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private CustomerEntity customer;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private OfferEntity resultingOffer;

    public Long getId() {
        return id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public OfferEntity getResultingOffer() {
        return resultingOffer;
    }

    public void setResultingOffer(OfferEntity resultingOffer) {
        this.resultingOffer = resultingOffer;
    }
}
