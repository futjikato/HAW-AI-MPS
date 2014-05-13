package de.haw.maps.test.model;

import de.haw.mps.persistence.AbstractModel;
import org.junit.Test;

import java.io.Serializable;

public abstract class ModelTestBase {

    protected abstract AbstractModel getModel();

    protected abstract Serializable getEntity();

    @Test
    public void testAdd() {
        AbstractModel model = getModel();
        Serializable serializable = getEntity();

        model.add(serializable);
    }

}
