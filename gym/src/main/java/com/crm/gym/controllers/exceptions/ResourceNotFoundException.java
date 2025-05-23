package com.crm.gym.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException
{
    public ResourceNotFoundException()
    {
        super(HttpStatus.NOT_FOUND, "Resource not found");
    }
}