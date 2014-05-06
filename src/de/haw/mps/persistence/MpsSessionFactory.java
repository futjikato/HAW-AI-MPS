package de.haw.mps.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.persistence
 */
public class MpsSessionFactory {

    private static SessionFactory sessionFactory;

    private MpsSessionFactory() {
    }

    public static Session getcurrentSession() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory.getCurrentSession();
    }

}
