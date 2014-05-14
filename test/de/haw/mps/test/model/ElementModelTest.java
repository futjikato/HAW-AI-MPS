package de.haw.mps.test.model;

import de.haw.mps.fabrication.entity.AssemblyPlanEntity;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.fabrication.model.AssemblyPlanModel;
import de.haw.mps.fabrication.model.ElementModel;
import de.haw.mps.persistence.AbstractModel;
import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class ElementModelTest extends ModelTestBase {


    @Override
    protected AbstractModel getModel() {
        return new ElementModel();
    }

    @Override
    protected Serializable getEntity() {
        ElementEntity elementEntity = new ElementEntity();
        elementEntity.setName("JUnit ElementModelTest:base");

        return elementEntity;
    }

    @Test
    public void testCreateEmptyPlan() {
        // prep
        ElementModel model = new ElementModel();
        ElementEntity entity = model.createElement("JUnit ElementModelTest:testCreateEmptyPlan");

        // save
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();

        // load
        session = MpsSessionFactory.getcurrentSession();
        Transaction transaction1 = session.beginTransaction();
        List elementList = session.createCriteria(ElementEntity.class)
               .add(Restrictions.like("name", "JUnit ElementModelTest:testCreateEmptyPlan"))
               .list();
        transaction1.commit();

        // check
        Assert.assertEquals(1, elementList.size());
        ElementEntity res = (ElementEntity) elementList.get(0);
        Assert.assertEquals(res.getName(), "JUnit ElementModelTest:testCreateEmptyPlan");
    }

    @Test
    public void testCreateWithPlan() {
        // prep
        ElementModel elementModel = new ElementModel();
        ElementEntity part1 = elementModel.createElement("JUnit ElementModelTest:testCreateWithPlan sub1");
        ElementEntity part2 = elementModel.createElement("JUnit ElementModelTest:testCreateWithPlan sub2");
        elementModel.add(part1);
        elementModel.add(part2);

        ElementEntity elementEntity = elementModel.createElement("JUnit ElementModelTest:testCreateWithPlan main");
        elementModel.add(elementEntity);

        AssemblyPlanModel planModel = new AssemblyPlanModel();
        HashSet<ElementEntity> entities = new HashSet<ElementEntity>();
        entities.add(part1);
        entities.add(part2);
        AssemblyPlanEntity planEntity = planModel.createPlan(elementEntity, entities);
        planModel.add(planEntity);
        elementModel.update(elementEntity);

        // load
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction1 = session.beginTransaction();
        session.refresh(part2);
        List elementList = session.createCriteria(ElementEntity.class)
                                  .createAlias("plan", "plan")
                                  .createAlias("plan.components", "cmp")
                                  .add(Restrictions.eq("cmp.id", part2.getId()))
                                  .list();
        transaction1.commit();

        Assert.assertEquals(1, elementList.size());
    }
}
