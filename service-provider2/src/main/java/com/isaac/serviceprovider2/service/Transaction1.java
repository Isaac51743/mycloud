package com.isaac.serviceprovider2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class Transaction1 {

    @Autowired
    Transaction2 transaction2;

//    @Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public void a() {

        System.out.println("this is method A in " + TransactionSynchronizationManager.getCurrentTransactionName());
//        transaction2.b();
        c();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void c() {
        System.out.println("this is method C in " + TransactionSynchronizationManager.getCurrentTransactionName());
    }
}
