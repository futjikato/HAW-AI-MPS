package de.haw.mps.fabrication.model;

import de.haw.mps.fabrication.entity.AssemblyOrderEntity;
import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.persistence.AbstractModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AssemblyOrderModel extends AbstractModel<AssemblyOrderEntity> {

    private static final int DEFAULT_DEADLINEDAYS = 14;

    public AssemblyOrderEntity createOrder(ElementEntity elementEntity, Calendar deadlineDate, Calendar orderDate) {
        AssemblyOrderEntity entity = new AssemblyOrderEntity();
        entity.setElement(elementEntity);
        entity.setOrderDate(orderDate);
        entity.setDeadlineDate(deadlineDate);

        return entity;
    }

    public AssemblyOrderEntity createOrder(ElementEntity elementEntity, Calendar deadlineDate) {
        return createOrder(elementEntity, deadlineDate, new GregorianCalendar());
    }

    public AssemblyOrderEntity createOrder(ElementEntity elementEntity) {
        Calendar deadlineDate = new GregorianCalendar();
        deadlineDate.add(GregorianCalendar.DAY_OF_MONTH, DEFAULT_DEADLINEDAYS);

        return createOrder(elementEntity, deadlineDate);
    }
}
