package com.chernomurov.effectivemobile.test.task.management.system.service.implementation;

import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRepository;
import com.chernomurov.effectivemobile.test.task.management.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("ERROR -> User not found (Username: '" +
                        username +
                        "'); Class: " +
                        this.getClass().getName()));
    }
}
