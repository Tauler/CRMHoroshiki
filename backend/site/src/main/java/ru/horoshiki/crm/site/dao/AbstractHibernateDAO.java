/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.horoshiki.crm.site.dao;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author donyushkin
 */
public abstract class AbstractHibernateDAO<T> implements GenericDAO<T>{

    protected SessionFactory sessionFactory;

    public AbstractHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Class<T> persistentClass;

    protected final Class<T> getPersistentClass() {
        return cast(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
    
    protected void appendJoinForPropertiesToCriteria(Criteria criteria, String... properties) {
        if (ArrayUtils.isEmpty(properties)) return;

        for (String propertyName : properties) {
            criteria.setFetchMode(propertyName, FetchMode.JOIN);
        }
    }

    /**
     * Метод левого присоединения таблиц
     * @param criteria Criteria
     * @param properties Таблицы, которые притягиваем
     */
    protected void appendLeftJoinAliasForPropertiesToCriteria(Criteria criteria, String... properties) {
        if (ArrayUtils.isEmpty(properties)) return;

        for (String propertyName : properties) {
            criteria.createAlias(propertyName, propertyName, JoinType.LEFT_OUTER_JOIN);
        }
    }

    /**
     * Метод получения всех полей из БД
     * @param properties Какие таблицы еще притягивать
     * @return Возвращает list с объектами с БД
     */
    @Override
    public List<T> getAll(String... properties) {
        Criteria searchCriteria = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        appendJoinForPropertiesToCriteria(searchCriteria, properties);
        searchCriteria.addOrder(Order.asc("id"));
        searchCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return searchCriteria.list();
    }

    /**
     * Метод получения количества полей в таблице
     * @return Возвращает целое число - количество полей в таблице
     */
    @Override
    public int size() {
        Criteria searchCriteria = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        return searchCriteria.list().size();
    }

    /**
     * Метод получения из БД объекта, по его id
     * @param id id поля
     * @param properties Какие таблицы еще притягивать
     * @return Возвращает объект по id
     */
    @Override
    public T get(Long id, String... properties) {
        Criteria searchCriteria = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        appendJoinForPropertiesToCriteria(searchCriteria, properties);
        searchCriteria.add(Restrictions.eq("id", id));
        return (T)searchCriteria.uniqueResult();
    }

    /**
     * Метод добавления в БД, в конкретную таблицу, объекта
     * @param item Объект, который нужно добавить в БД
     */
    @Override
    public void add(T item) {
        sessionFactory.getCurrentSession().save(item);
    }

    /**
     * Метод обновления объекта в БД
     * @param item Объект, который необходимо обновить
     */
    @Override
    public void update(T item) {
        sessionFactory.getCurrentSession().saveOrUpdate(item);
    }

    /**
     * Метод удаления объекта из БД
     * @param item Объект, который необходимо удалить
     */
    @Override
    public void delete(T item) {
        sessionFactory.getCurrentSession().delete(item);
    }

    /**
     *
     * @param criteria DetachedCriteria
     * @return
     */
    @Override
    public List<T> find(DetachedCriteria criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
