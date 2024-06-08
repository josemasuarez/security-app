package com.uai.lppa.security.service;

import com.uai.lppa.security.exception.BadRequestException;
import com.uai.lppa.security.model.Privilege;
import com.uai.lppa.security.model.User;
import com.uai.lppa.security.repository.PrivilegeRepository;
import com.uai.lppa.security.repository.UserRepository;
import com.uai.lppa.security.controller.request.UserRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerNewUser(final UserRequest user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new BadRequestException("User already exists");
                });

        final User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User addPrivilegesToUser(final Long userId, final Set<Long> privilegeIds) throws BadRequestException {
        final User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        final List<Privilege> privileges = Optional.of(privilegeRepository.findAllById(privilegeIds))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new BadRequestException("Privilege does not exist"));
        user.setPrivileges(privileges);
        return userRepository.save(user);
    }

    public boolean hasPrivilege(User user, String privilege) {
        return user.hasPrivilege(privilege);
    }
}
