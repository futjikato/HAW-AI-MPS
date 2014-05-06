package de.haw.mps.fabrication.entity;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import java.io.Serializable;

@Entity(name = "AssemblyOrder")
@Table(appliesTo = "AssemblyOrder")
public class AssemblyOrderEntity implements Serializable {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Long id;

    @Column(nullable = false)
    private String street;

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    private Element element;

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}