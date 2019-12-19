package br.com.challenge.uploadservice.exception;

import java.util.Collection;

abstract class AbstractException extends RuntimeException {

    private Collection<String> details;

    AbstractException(String message, Collection<String> details) {
        super(message);
        this.details = details;
    }

    public Collection<String> getDetails() {
        return details;
    }
}
