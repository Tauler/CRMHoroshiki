package ru.horoshiki.crm.site.dao;


import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ru.horoshiki.crm.site.model.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


/**
 * Created by bogdanovkp on 28.11.2016.
 */
public class UserDAOImpl extends AbstractHibernateDAO<User> implements UserDAO {
    public UserDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    /**
     * Метод получения пользователя по mail из БД
     * @param login login пользователя
     * @param properties Таблицы, которые необходимо притянуть
     * @return Возвращает пользователя
     */
    @Override
    public User getUserByLogin(String login, String... properties) {
        Criteria searchCriteria = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        appendJoinForPropertiesToCriteria(searchCriteria, properties);
        searchCriteria.add(Restrictions.eq("login", login).ignoreCase());
        return (User) searchCriteria.uniqueResult();

    }

    @Override
    public boolean isUserByLogin(String login, boolean isBlank) {
        Criteria searchCriteria = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        searchCriteria.add(Restrictions.eq("login", login).ignoreCase());
        if(!isBlank)
            searchCriteria.add(Restrictions.eq("blank", false));
        User user = (User) searchCriteria.uniqueResult();
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public User getNotConfirmUserById(Long id, String... properties) {
        Criteria searchCriteria = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        searchCriteria.add(Restrictions.eq("id", id));
        searchCriteria.add(Restrictions.eq("blank", true));
        User user = (User) searchCriteria.uniqueResult();
        return user;
    }
}
