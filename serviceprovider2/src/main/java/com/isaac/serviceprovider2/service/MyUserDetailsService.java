package com.isaac.serviceprovider2.service;

import com.isaac.serviceprovider2.dao.HibernateDao;
import com.isaac.serviceprovider2.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    HibernateDao hibernateDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Account> accountList = hibernateDao.getByName(username);
        if (accountList.size() == 0) {
            throw new UsernameNotFoundException("this user doesn't existed");
        }
        Account target = accountList.get(0);
        System.out.println(target);
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(target.getRoles());
        return new User(target.getUserName(), target.getPassword(), authorityList);
    }
}
