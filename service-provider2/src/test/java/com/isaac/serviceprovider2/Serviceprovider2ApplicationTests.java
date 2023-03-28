package com.isaac.serviceprovider2;

import com.isaac.serviceprovider2.dao.HibernateDao;
import com.isaac.serviceprovider2.dao.JdbcDao;
import com.isaac.serviceprovider2.service.Transaction1;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Serviceprovider2ApplicationTests {

    @Autowired
    Transaction1 transaction1;

    @Autowired
    HibernateDao hibernateDao;

    @Autowired
    JdbcDao jdbcDao;

    @Test
    void transactionTest() {
        transaction1.a();
    }

    @Test
    void jdbcTest() {
        System.out.println(jdbcDao.getList());
        System.out.println(jdbcDao.getObject());
    }

    @Test
    void hibernateTest() {
        System.out.println(hibernateDao.get());
    }
}
