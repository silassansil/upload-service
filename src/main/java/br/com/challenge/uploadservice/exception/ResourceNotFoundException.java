package br.com.challenge.uploadservice.exception;

import java.util.Collection;

public class ResourceNotFoundException extends AbstractException {

    private static final String RESOURCE_NOT_FOUND = "resource not found";

    public ResourceNotFoundException(Collection<String> details) {
        super(RESOURCE_NOT_FOUND, details);
    }
}
