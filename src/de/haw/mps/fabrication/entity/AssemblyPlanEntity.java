package de.haw.mps.fabrication.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "AssemblyPlan")
@Table(appliesTo = "AssemblyPlan")
public class AssemblyPlanEntity implements Serializable {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ElementEntity baseElement;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private Set<ElementEntity> components;

    public Long getId() {
        return id;
    }

    public ElementEntity getBaseElement() {
        return baseElement;
    }

    public void setBaseElement(ElementEntity baseElement) {
        this.baseElement = baseElement;
    }

    public Set<ElementEntity> getComponents() {
        return components;
    }

    public void setComponents(Set<ElementEntity> components) {
        this.components = components;
    }
}
