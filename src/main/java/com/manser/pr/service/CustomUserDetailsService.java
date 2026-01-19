package com.manser.pr.service;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    public CustomUserDetailsService() {}

    @Autowired
    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Знаходимо користувача в базі за email
        User user = userDao.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 2. Перетворюємо UserRole на GrantedAuthority
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name());

        // 3. Повертаємо Spring Security UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),      // username
                user.getPassword(),   // password (можна зашифрувати BCrypt)
                Collections.singletonList(authority) // authorities
        );
    }
}
