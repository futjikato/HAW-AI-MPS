package de.haw.mps.offer.model;

import de.haw.mps.offer.entity.CustomerEntity;
import de.haw.mps.persistence.AbstractModel;
import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CustomerModel extends AbstractModel<CustomerEntity> {

    public CustomerModel() {
        super(CustomerEntity.class);
    }

    public CustomerEntity createCustomer(String name) {
        CustomerEntity entity = new CustomerEntity();
        entity.setName(name);

        return entity;
    }

    public List getCustomers() {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();

        Criteria crit = session.createCriteria(CustomerEntity.class);
        List customers = crit.list();

        transaction.commit();

        return customers;
    }

}
