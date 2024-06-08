package com.uai.lppa.security.exception;

import java.util.Map;

import static java.lang.String.format;

public class EntityNotFoundException extends RuntimeException {

    public static final String MESSAGE_PATTERN = "Entity %s not found for %s";

    private String entityName;
    private Map<String, Object> entityParams;

    public EntityNotFoundException(final String entityName,
                                   final Map<String, Object> entityParams) {
        super();
        this.entityName = entityName;
        this.entityParams = entityParams;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Map<String, Object> getEntityParams() {
        return entityParams;
    }

    public void setEntityParams(Map<String, Object> entityParams) {
        this.entityParams = entityParams;
    }

    @Override
    public String getMessage() {
        return format(
                MESSAGE_PATTERN,
                entityName,
                entityParams.toString()
        );
    }

}
