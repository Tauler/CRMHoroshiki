package ru.horoshiki.crm.site.dao;

import org.hibernate.SessionFactory;
import ru.horoshiki.crm.site.model.UserRole;

/**
 * Created by bogdanovkp on 28.11.2016.
 */
public class UserRoleDAOImpl extends AbstractHibernateDAO<UserRole> implements UserRoleDAO {
    public UserRoleDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
