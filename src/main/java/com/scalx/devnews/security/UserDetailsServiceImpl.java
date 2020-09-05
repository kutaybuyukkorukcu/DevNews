package com.scalx.devnews.security;

import com.scalx.devnews.entity.Privilege;
import com.scalx.devnews.entity.Role;
import com.scalx.devnews.entity.User;
import com.scalx.devnews.repository.RoleRepository;
import com.scalx.devnews.repository.UserRepository;
import com.scalx.devnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

// @Service("userDetailsService")
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        // TODO : Wrap below in try/catch if needed - Test first.

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
//                getAuthorities(user.getRoles())
                new ArrayList<>());
    }
}
