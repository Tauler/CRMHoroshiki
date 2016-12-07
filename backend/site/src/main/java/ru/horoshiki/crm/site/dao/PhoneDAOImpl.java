package ru.horoshiki.crm.site.dao;

import org.hibernate.SessionFactory;
import ru.horoshiki.crm.site.model.entity.Phone;

/**
 * Created by onyushkindv on 07.12.2016.
 */
public class PhoneDAOImpl extends  AbstractHibernateDAO<Phone> implements PhoneDAO {

    public PhoneDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
