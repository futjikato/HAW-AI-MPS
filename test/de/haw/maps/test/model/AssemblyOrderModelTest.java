package de.haw.maps.test.model;

import de.haw.mps.fabrication.entity.AssemblyOrderEntity;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.fabrication.model.AssemblyOrderModel;
import de.haw.mps.persistence.AbstractModel;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

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

        boolean succ = model.add(orderEntity);
        Assert.assertFalse(succ);
    }
}
