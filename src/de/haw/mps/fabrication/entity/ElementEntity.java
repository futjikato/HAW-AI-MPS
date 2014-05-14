package de.haw.mps.fabrication.entity;

import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "Element")
@Table(appliesTo = "Element")
public class ElementEntity implements Serializable {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private AssemblyPlanEntity plan;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public AssemblyPlanEntity getPlan() {
        return plan;
    }

    public void setPlan(AssemblyPlanEntity plan) {
        this.plan = plan;

        if(plan.getBaseElement() == null) {
            plan.setBaseElement(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
