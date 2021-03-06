package com.service.admin.service;

import com.service.admin.model.Role;
import com.service.admin.model.User;
import com.service.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        if (user != null) {
            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setAuthority("ROLE_USER");

            roles.add(role);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String cryptPwd = bCryptPasswordEncoder.encode(user.getPassword());

            user.setPassword(cryptPwd);
            user.setRoles(roles);

            return userRepository.saveAndFlush(user);
        }
        return user;
    }

    @Override
    public User update(User user) {
        if (user != null) {
            return userRepository.saveAndFlush(user);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        if (id != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id != null) {
            return userRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<User> fetchUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        if (!StringUtils.isEmpty(username)) {
            return userRepository.findUserByUsername(username);
        }
        return Optional.empty();
    }
}
