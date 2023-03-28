package com.isaac.serviceprovider2.dao;

import com.isaac.serviceprovider2.domain.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HibernateDao {

    @Autowired
    SessionFactory sessionFactory;

    public Account get() {
//        Session session = sessionFactory.getCurrentSession();
        Session session = sessionFactory.getCurrentSession();
        return session.get(Account.class, 1);
    }

    public List<Account> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Account where userName = ?1");
        query.setParameter(1, name);
        List<Account> accountList = query.list();
        return accountList;
    }
}
