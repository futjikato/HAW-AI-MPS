package de.haw.mps.fabrication.model;

import de.haw.mps.fabrication.entity.AssemblyPlanEntity;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.persistence.AbstractModel;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.fabrication.model
 */
public class AssemblyPlanModel extends AbstractModel<AssemblyPlanEntity> {

    public AssemblyPlanModel() {
        super(AssemblyPlanEntity.class);
    }

    public AssemblyPlanEntity createPlan(ElementEntity baseElement, Set<ElementEntity> constructionParts) {
        AssemblyPlanEntity entity = new AssemblyPlanEntity();
        entity.setBaseElement(baseElement);
        entity.setComponents(constructionParts);

        return entity;
    }

    public AssemblyPlanEntity createPlan(ElementEntity baseElement) {
        return createPlan(baseElement, new LinkedHashSet<ElementEntity>());
    }

}
