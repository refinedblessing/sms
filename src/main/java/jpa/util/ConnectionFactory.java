package jpa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public enum ConnectionFactory {
    GET_SESSION();
    Session session;
    SessionFactory sessionFactory;
    Transaction transaction;

    private ConnectionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        try {
            sessionFactory = configuration.buildSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (session != null) session.close();
            if (sessionFactory != null) sessionFactory.close();
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
        sessionFactory.close();
    }
}