package de.haw.mps.test.model;

import de.haw.mps.persistence.AbstractModel;
import de.haw.mps.persistence.WorkflowException;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

public abstract class ModelTestBase {

    protected abstract AbstractModel getModel();

    protected abstract Serializable getEntity();

    @Test
    public void testAdd() {
        AbstractModel model = getModel();
        Serializable serializable = getEntity();

        boolean succ = false;
        try {
            model.startTransaction();
            succ = model.add(serializable);
            model.commitTransaction();
        } catch (WorkflowException e) {
            e.printStackTrace();
            Assert.fail();
            return;
        }

        Assert.assertTrue(succ);
    }

}
