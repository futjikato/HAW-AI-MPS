package de.haw.mps.test.model;

import de.haw.mps.fabrication.entity.AssemblyOrderEntity;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.fabrication.model.AssemblyOrderModel;
import de.haw.mps.fabrication.model.ElementModel;
import de.haw.mps.persistence.AbstractModel;
import de.haw.mps.persistence.MpsSessionFactory;
import de.haw.mps.persistence.WorkflowException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.maps.test
 */
public class AssemblyOrderModelTest extends ModelTestBase {

    @Override
    protected AssemblyOrderModel getModel() {
        return new AssemblyOrderModel();
    }

    @Override
    protected Serializable getEntity() {
        AssemblyOrderEntity orderEntity = new AssemblyOrderEntity();
        orderEntity.setOrderDate(new GregorianCalendar(2014, 5, 10));
        orderEntity.setDeadlineDate(new GregorianCalendar(2014, 5, 14));

        ElementEntity elementEntity = new ElementEntity();
        elementEntity.setName("JUnit Test-Element");

        orderEntity.setElement(elementEntity);

        return orderEntity;
    }

    @Test()
    public void testFailNoElement() {
        AbstractModel model = getModel();

        AssemblyOrderEntity orderEntity = new AssemblyOrderEntity();
        orderEntity.setOrderDate(new GregorianCalendar(2014, 5, 10));
        orderEntity.setDeadlineDate(new GregorianCalendar(2014, 5, 14));

        boolean succ = false;
        try {
            model.startTransaction();
            succ = model.add(orderEntity);
            model.commitTransaction();
        } catch (WorkflowException e) {
            Assert.fail();
        }
        Assert.assertFalse(succ);
    }

    @Test
    public void testCreate() {
        // create element
        ElementModel elementModel = new ElementModel();
        ElementEntity elementEntity = elementModel.createElement("JUnit AssemblyOrderModelTest:testCreate");

        // create order
        AssemblyOrderModel model = new AssemblyOrderModel();
        AssemblyOrderEntity entity = model.createOrder(elementEntity);

        // get session
        Session session = MpsSessionFactory.getcurrentSession();

        // save order
        Transaction t = session.beginTransaction();
        session.save(entity);
        t.commit();


        // fetch element with unit test name
        session = MpsSessionFactory.getcurrentSession();
        Transaction t1 = session.beginTransaction();
        List elementEntities = session.createCriteria(ElementEntity.class)
                                      .add(Restrictions.eq("name", "JUnit AssemblyOrderModelTest:testCreate"))
                                      .list();
        Assert.assertEquals(1, elementEntities.size());
        t1.commit();
    }
}
