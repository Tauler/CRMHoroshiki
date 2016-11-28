/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.horoshiki.crm.site.dao;

import org.hibernate.criterion.DetachedCriteria;

import java.util.List;


public interface GenericDAO<T> {
    /**
     * Метод получения всех полей из БД
     * @param properties Какие таблицы еще притягивать
     * @return Возвращает list с объектами с БД
     */
    List<T> getAll(String... properties);

    /**
     * Метод получения количества полей в таблице
     * @return Возвращает целое число - количество полей в таблице
     */
    int size();

    /**
     * Метод получения из БД объекта, по его id
     * @param id id поля
     * @param properties Какие таблицы еще притягивать
     * @return Возвращает объект по id
     */
    T get(Long id, String... properties);

    /**
     * Метод добавления в БД, в конкретную таблицу, объекта
     * @param item Объект, который нужно добавить в БД
     */
    void add(T item);

    /**
     * Метод обновления объекта в БД
     * @param item Объект, который необходимо обновить
     */
    void update(T item);

    /**
     * Метод удаления объекта из БД
     * @param item Объект, который необходимо удалить
     */
    void delete(T item);

    List<T> find(DetachedCriteria criteria);
}
