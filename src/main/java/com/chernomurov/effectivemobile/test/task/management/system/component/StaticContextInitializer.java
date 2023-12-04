package com.chernomurov.effectivemobile.test.task.management.system.component;

import com.chernomurov.effectivemobile.test.task.management.system.configuration.ApplicationConfig;
import com.chernomurov.effectivemobile.test.task.management.system.util.JwtUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaticContextInitializer {

    private final ApplicationConfig applicationConfig;

    @PostConstruct
    public void init() {
        JwtUtils.setSigningKey(applicationConfig.getSigningKey());
        JwtUtils.setExpiration(applicationConfig.getJwtExpiration());
        JwtUtils.setRefreshExpiration(applicationConfig.getRefreshExpiration());
    }
}