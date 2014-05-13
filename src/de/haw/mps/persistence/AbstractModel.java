package de.haw.mps.persistence;

import de.haw.mps.MpsLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public abstract class AbstractModel<T extends Serializable> {

    public boolean add(T entity) {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            MpsLogger.handleException(e);
            return false;
        }
    }
}
