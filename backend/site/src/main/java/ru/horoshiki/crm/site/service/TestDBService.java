package ru.horoshiki.crm.site.service;

/**
 * Created by onyushkindv on 07.08.2015.
 */
public interface TestDBService{

    /**
     * Метод определения доступности сервера
     * @return true - сервер и БД доступны, false - недоступны
     */
    boolean isDBAvailable();
}
