/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.horoshiki.crm.site.service;

import org.springframework.transaction.annotation.Transactional;
import ru.horoshiki.crm.site.dao.GenericDAO;

import java.util.List;

public class AbstractService<T, D extends GenericDAO<T>> implements GenericService<T> {
    
    private D dao;

    public D getDao() {
        return dao;
    }

    public void setDao(D dao) {
        this.dao = dao;
    }

    /**
     * Метод добавления в БД, в конкретную таблицу, объекта
     * @param item Объект, который нужно добавить в БД
     */
    @Override
    @Transactional
    public void add(T item) {
       dao.add(item);
    }

    /**
     * Метод получения количества полей в таблице
     * @return Возвращает целое число - количество полей в таблице
     */
    @Override
    @Transactional
    public int size() {
        return dao.size();
    }

    /**
     * Метод обновления объекта в БД
     * @param item Объект, который необходимо обновить
     */
    @Override
    @Transactional
    public void update(T item) {
        dao.update(item);
    }

    /**
     * Метод получения всех полей из БД
     * @param properties Какие таблицы еще притягивать
     * @return Возвращает list с объектами с БД
     */
    @Override
    @Transactional
    public List<T> getAll(String... properties) {
        return dao.getAll(properties);
    }

    /**
     * Метод удаления объекта из БД
     * @param item Объект, который необходимо удалить
     */
    @Override
    @Transactional
    public void delete(T item) {
        dao.delete(item);
    }

    /**
     * Метод получения из БД объекта, по его id
     * @param id id поля
     * @param properties Какие таблицы еще притягивать
     * @return Возвращает объект по id
     */
    @Override
    @Transactional
    public T get(Long id, String... properties) {
        return dao.get(id, properties);
    }
}
