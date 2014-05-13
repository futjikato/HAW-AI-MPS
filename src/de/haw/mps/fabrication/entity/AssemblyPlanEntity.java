package de.haw.mps.fabrication.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

@Entity(name = "AssemblyPlan")
@Table(appliesTo = "AssemblyPlan")
public class AssemblyPlanEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "idGen", strategy = "increment")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ElementEntity baseElement;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private Set<ElementEntity> components;

}
