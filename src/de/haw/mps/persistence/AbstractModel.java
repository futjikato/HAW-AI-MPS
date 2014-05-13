package de.haw.mps.persistence;

import de.haw.mps.MpsLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public abstract class AbstractModel<T extends Serializable> {

    Class<T> tClass;

    public AbstractModel(Class<T> tClass) {
        this.tClass = tClass;
    }

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

    public boolean update(T entity) {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            MpsLogger.handleException(e);
            return false;
        }
    }

    public boolean delete(T entity) {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            MpsLogger.handleException(e);
            return false;
        }
    }

    public T get(Long id) {
        Session session = MpsSessionFactory.getcurrentSession();

        try {
            return (T) session.get(tClass, id);
        } catch (Exception e) {
            MpsLogger.handleException(e);
            return null;
        }
    }
}
