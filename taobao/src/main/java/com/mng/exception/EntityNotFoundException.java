package com.mng.exception;

public class EntityNotFoundException extends IllegalArgumentException {
    private String entityName;

    public EntityNotFoundException(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Entity not found: " + entityName;
    }
}
