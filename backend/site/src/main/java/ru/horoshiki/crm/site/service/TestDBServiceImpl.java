package ru.horoshiki.crm.site.service;

import org.springframework.transaction.annotation.Transactional;
import ru.horoshiki.crm.site.dao.TestDBDAO;


/**
 * Created by onyushkindv on 07.08.2015.
 */
public class TestDBServiceImpl implements TestDBService {
    protected TestDBDAO dao;

    public TestDBDAO getDao() {
        return dao;
    }

    public void setDao(TestDBDAO dao) {
        this.dao = dao;
    }

    /**
     * Метод определения доступности сервера
     * @return true - сервер и БД доступны, false - недоступны
     */
    @Override
    @Transactional
    public boolean isDBAvailable() {
        return getDao().isDBAvailable();
    }
}
