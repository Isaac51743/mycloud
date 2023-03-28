package com.isaac.serviceprovider2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class Transaction2 {
    @Transactional(propagation = Propagation.MANDATORY)
    public void b() {
        System.out.println("this is method B in " + TransactionSynchronizationManager.getCurrentTransactionName());
    }
}
