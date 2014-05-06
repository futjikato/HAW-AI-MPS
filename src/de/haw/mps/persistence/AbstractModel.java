package de.haw.mps.persistence;

import de.haw.mps.MpsLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public abstract class AbstractModel<T extends Serializable> {

    public boolean add(T entity) {
        try {
            Session session = MpsSessionFactory.getcurrentSession();
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            MpsLogger.handleException(e);
            return false;
        }
    }
}
