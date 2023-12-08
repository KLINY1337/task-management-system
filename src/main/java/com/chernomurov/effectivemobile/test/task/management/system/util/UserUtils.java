package com.chernomurov.effectivemobile.test.task.management.system.util;

import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.CustomerNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.UserRoleCheckException;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRoleRepository;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

@UtilityClass
public class UserUtils {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    public void setUserRepository(UserRepository userRepository) {
        UserUtils.userRepository = userRepository;
    }
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        UserUtils.userRoleRepository = userRoleRepository;
    }

    @NotNull
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Map<String, Object> currentUserPrincipal = (Map<String, Object>) authentication.getPrincipal();
            String currentUserEmail = (String) currentUserPrincipal.get("username");
            return userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("ERROR -> Authenticated user not found in database (Email: '" + currentUserEmail + "') ; Class: " + UserUtils.class.getName()));
        }
        throw new UsernameNotFoundException("CRITICAL ERROR -> UNREACHABLE APPLICATION STATEMENT ; Class: " + UserUtils.class.getName());
    }

    @NotNull
    public User getUserByIdAndCheckRole(Long id, String roleName) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("ERROR -> " + roleName + " not found (id: '" + id + "') ; Class: " + UserUtils.class.getName()));

        if (!user.getRoles().contains(userRoleRepository.findByName(roleName).get())) {
            throw new UserRoleCheckException("ERROR -> Selected user is not a " + roleName.toLowerCase() + " (id: '" + id + "') ; Class: " + UserUtils.class.getName());
        }
        return user;
    }


}
