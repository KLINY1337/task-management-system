package com.chernomurov.effectivemobile.test.task.management.system.component;

import com.chernomurov.effectivemobile.test.task.management.system.configuration.ApplicationConfig;
import com.chernomurov.effectivemobile.test.task.management.system.util.JwtUtils;
import com.chernomurov.effectivemobile.test.task.management.system.util.UserUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaticContextInitializer {

    private final ApplicationConfig applicationConfig;

    @PostConstruct
    public void initJwtUtils() {
        JwtUtils.setSigningKey(applicationConfig.getSigningKey());
        JwtUtils.setExpiration(applicationConfig.getJwtExpiration());
        JwtUtils.setRefreshExpiration(applicationConfig.getRefreshExpiration());
    }

    @PostConstruct
    public void initUserUtils() {
        UserUtils.setUserRepository(applicationConfig.getUserRepository());
    }
}