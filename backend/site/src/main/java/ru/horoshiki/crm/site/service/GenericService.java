/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.horoshiki.crm.site.service;

import java.util.List;

/**
 *
 * @author goofy
 */
public interface GenericService<T> {
    /**
     * Метод добавления в БД, в конкретную таблицу, объекта
     * @param item Объект, который нужно добавить в БД
     */
    void add(T item);

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
     * Метод обновления объекта в БД
     * @param item Объект, который необходимо обновить
     */
     void update(T item);

    /**
     * Метод удаления объекта из БД
     * @param item Объект, который необходимо удалить
     */
     void delete(T item);
}
