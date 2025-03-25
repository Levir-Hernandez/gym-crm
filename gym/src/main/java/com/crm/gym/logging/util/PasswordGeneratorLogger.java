package com.crm.gym.logging.util;

import com.crm.gym.util.PasswordGenerator;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PasswordGeneratorLogger
{
    private final Logger logger = LoggerFactory.getLogger(PasswordGenerator.class);

    @Pointcut("within(com.crm.gym.util.PasswordGenerator+) && execution(String generatePassword())")
    public void generatePassword() {}

    @AfterReturning(pointcut = "generatePassword()", returning = "password")
    public void afterReturning_generateUsername(String password)
    {
        //password = "*".repeat(password.length());
        logger.debug("Generated new password: {}", password);
    }
}
