package com.crm.gym.logging.util;

import com.crm.gym.util.UsernameGenerator;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UsernameGeneratorLogger
{
    private final Logger logger = LoggerFactory.getLogger(UsernameGenerator.class);

    @Pointcut("within(com.crm.gym.util.UsernameGenerator+) && execution(String generateUsername())")
    public void generateUsername() {}

    @AfterReturning(pointcut = "generateUsername()", returning = "username")
    public void afterReturning_generateUsername(String username)
    {
        //username = "*".repeat(username.length());
        logger.debug("Generated new username: {}", username);
    }
}
