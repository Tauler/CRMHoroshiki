package ru.horoshiki.crm.site.dao;

import org.hibernate.SessionFactory;


public class TestDBDAOImpl implements TestDBDAO{
    protected SessionFactory sessionFactory;

    public TestDBDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Метод определения доступности сервера
     * @return true - сервер и БД доступны, false - недоступны
     */
    @Override
    public boolean isDBAvailable() {
        return sessionFactory.getCurrentSession().isConnected();
    }
}
