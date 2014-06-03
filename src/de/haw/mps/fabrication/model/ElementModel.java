package de.haw.mps.fabrication.model;

import de.haw.mps.fabrication.entity.AssemblyPlanEntity;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.persistence.AbstractModel;
import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Set;

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

    public ElementEntity getElementByName(String name) {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();

        Criteria crit = session.createCriteria(ElementEntity.class);
        crit.add(Restrictions.eq("name", name));
        List elements = crit.list();

        transaction.commit();

        if(elements.size() <= 0) {
            return null;
        } else {
            Object object = elements.get(0);
            if(object instanceof ElementEntity) {
                return (ElementEntity)object;
            } else {
                return null;
            }
        }
    }
}
