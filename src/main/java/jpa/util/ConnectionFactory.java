package jpa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public enum ConnectionFactory {

    GET_SESSION(),
    CLOSE_SESSION();

    private final Session session;
    private final Transaction transaction;

    private ConnectionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
        }
    }

    public ConnectionFactory getInstance() {
        return GET_SESSION;
    }

    public Session getSession() {
        return session;
    }

    public void closeSession() {
        transaction.commit();
        session.close();
    }

}