package com.farnell.server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(EntityNotFoundException.class);

    public EntityNotFoundException(String name) {
        logger.error(String.format("Entity %s not found", name));
    }

    public EntityNotFoundException(Long entityId) {
        logger.error(String.format("Entity with id %d not found", entityId));
    }

    public EntityNotFoundException(Class<?> entity) {
        super(String.format("Entity of type %s not found", entity.getSimpleName()));
        logger.error(String.format("Entity of type %s not found", entity.getSimpleName()));
    }

    public EntityNotFoundException(Class<?> entity, String name) {
        super(String.format("Entity of type %s with name %s not found", entity.getSimpleName(), name));
        logger.error(String.format("Entity of type %s with name %s not found", entity.getSimpleName(), name));
    }

    public EntityNotFoundException(Class<?> entity, Long id) {
        super(String.format("Entity of type %s with id %d not found", entity.getSimpleName(), id));
        logger.error(String.format("Entity of type %s with id %d not found", entity.getSimpleName(), id));
    }

}