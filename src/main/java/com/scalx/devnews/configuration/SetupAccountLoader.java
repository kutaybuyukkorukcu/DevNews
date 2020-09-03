//package com.scalx.devnews.configuration;
//
//import com.scalx.devnews.entity.Privilege;
//import com.scalx.devnews.entity.Role;
//import com.scalx.devnews.entity.User;
//import com.scalx.devnews.repository.PrivilegeRepository;
//import com.scalx.devnews.repository.RoleRepository;
//import com.scalx.devnews.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.CollectionTable;
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//@Component
//public class SetupAccountLoader implements ApplicationListener<ContextRefreshedEvent> {
//
//    private boolean alreadySetup = false;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PrivilegeRepository privilegeRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//
//        if (alreadySetup) {
//            return;
//        }
//
//        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
//        Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
//
//        List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
//        List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
//        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//        createRoleIfNotFound("ROLE_USER", userPrivileges);
//
//        createUserIfNotFound("test@test.com", "test", "test", new ArrayList<>(Arrays.asList(adminRole)));
//
//        alreadySetup = true;
//    }
//
//    @Transactional
//    Privilege createPrivilegeIfNotFound(String name) {
//        Privilege privilege = privilegeRepository.findByName(name);
//
//        if (privilege == null) {
//            privilege = new Privilege(name);
//            privilege = privilegeRepository.save(privilege);
//        }
//
//        return privilege;
//    }
//
//    @Transactional
//    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
//       Role role = roleRepository.findByName(name);
//
//       if (role == null) {
//           role = new Role(name);
//       }
//
//       role.setPrivileges(privileges);
//       role = roleRepository.save(role);
//
//       return role;
//    }
//
//    @Transactional
//    User createUserIfNotFound(String email, String username, String password, Collection<Role> roles) {
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            user = new User();
//
//            user.setUsername(username);
//            // TODO : Client sends encoded password to API. Using PasswordEncoder till Integration tests.
//            user.setPassword(passwordEncoder.encode(password));
//            user.setEmail(email);
//            user.setEnabled(true);
//        }
//
//        user.setRoles(roles);
//        user = userRepository.save(user);
//
//        return user;
//    }
//}
