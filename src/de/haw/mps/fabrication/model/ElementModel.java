package de.haw.mps.fabrication.model;

import de.haw.mps.fabrication.entity.AssemblyPlanEntity;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.persistence.AbstractModel;

import java.util.Set;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.fabrication.model
 */
public class ElementModel extends AbstractModel<ElementEntity> {

    public ElementModel() {
        super(ElementEntity.class);
    }

    public ElementEntity createElement(String name, AssemblyPlanEntity assemblyPlan) {
        ElementEntity entity = new ElementEntity();
        entity.setName(name);
        entity.setPlan(assemblyPlan);

        return entity;
    }

    public ElementEntity createElement(String name, Set<ElementEntity> constructionParts) {
        ElementEntity entity = createElement(name);

        AssemblyPlanModel planModel = new AssemblyPlanModel();
        AssemblyPlanEntity planEntity = planModel.createPlan(entity, constructionParts);

        entity.setPlan(planEntity);

        return entity;
    }

    public ElementEntity createElement(String name) {
        ElementEntity entity = new ElementEntity();
        entity.setName(name);

        return entity;
    }

}
