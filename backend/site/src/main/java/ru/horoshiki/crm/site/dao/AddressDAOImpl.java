package ru.horoshiki.crm.site.dao;

import org.hibernate.SessionFactory;
import ru.horoshiki.crm.site.model.entity.Address;

/**
 * Created by onyushkindv on 08.12.2016.
 */
public class AddressDAOImpl extends AbstractHibernateDAO<Address> implements AddressDAO {
    public AddressDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
