package de.haw.mps.offer.model;

import de.haw.mps.offer.entity.CustomerEntity;
import de.haw.mps.persistence.AbstractModel;

public class CustomerModel extends AbstractModel<CustomerEntity> {

    public CustomerEntity createCustomer(String name) {
        CustomerEntity entity = new CustomerEntity();
        entity.setName(name);

        return entity;
    }

}
