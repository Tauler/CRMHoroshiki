package ru.horoshiki.crm.site.dao;


public interface TestDBDAO {
    /**
     * Метод определения доступности сервера
     * @return true - сервер и БД доступны, false - недоступны
     */
    boolean isDBAvailable();
}
