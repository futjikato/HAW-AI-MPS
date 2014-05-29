package de.haw.mps.persistence;

import de.haw.mps.MpsLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.management.RuntimeErrorException;
import java.io.Serializable;

public abstract class AbstractModel<T extends Serializable> {

    protected Class<T> tClass;

    protected boolean hasActiveTransaction = false;

    public AbstractModel(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void startTransaction() {
        if(!hasActiveTransaction) {
            Session session = MpsSessionFactory.getcurrentSession();
            session.beginTransaction();
            hasActiveTransaction = true;
        }
    }

    public void commitTransaction() throws WorkflowException {
        if(!hasActiveTransaction) {
            throw new WorkflowException("No transaction open.");
        }

        try {
            Session session = MpsSessionFactory.getcurrentSession();
            Transaction transaction = session.getTransaction();
            transaction.commit();
        } catch (Exception e) {
            throw new WorkflowException(e);
        }
    }

    public void rollbackTransaction() throws WorkflowException {
        if(!hasActiveTransaction) {
            throw new WorkflowException("No transaction open.");
        }

        try {
            Session session = MpsSessionFactory.getcurrentSession();
            Transaction transaction = session.getTransaction();
            transaction.rollback();
        } catch (Exception e) {
            throw new WorkflowException(e);
        }
    }

    public boolean add(T entity) throws WorkflowException {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.getTransaction();
        if(transaction == null || !transaction.isActive()) {
            throw new WorkflowException("No transaction running while trying to insert new object.");
        }

        try {
            session.save(entity);
            session.refresh(entity);
            return true;
        } catch (Exception e) {
            throw new WorkflowException(e);
        }
    }

    public boolean update(T entity) throws WorkflowException {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.getTransaction();
        if(transaction == null || !transaction.isActive()) {
            throw new WorkflowException("No transaction running while trying to update object.");
        }

        try {
            session.update(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new WorkflowException(e);
        }
    }

    public boolean delete(T entity) throws WorkflowException {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.getTransaction();
        if(transaction == null || !transaction.isActive()) {
            throw new WorkflowException("No transaction running while trying to delete object.");
        }

        try {
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new WorkflowException(e);
        }
    }

    public T get(Long id) throws WorkflowException {
        Session session = MpsSessionFactory.getcurrentSession();
        Transaction transaction = session.getTransaction();
        if(transaction == null || !transaction.isActive()) {
            throw new WorkflowException("No transaction running while trying to get object.");
        }

        try {
            return (T) session.get(tClass, id);
        } catch (Exception e) {
            throw new WorkflowException(e);
        }
    }
}
