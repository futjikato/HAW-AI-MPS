package de.haw.mps.fabrication.entity;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "AssemblyOrder")
@Table(appliesTo = "AssemblyOrder")
public class AssemblyOrderEntity implements Serializable {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    private ElementEntity element;

    @Column(nullable = false)
    private Calendar orderDate;

    @Column(nullable = false)
    private Calendar deadlineDate;

    public Long getId() {
        return id;
    }

    public ElementEntity getElement() {
        return element;
    }

    public void setElement(ElementEntity element) {
        this.element = element;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public Calendar getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Calendar deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}