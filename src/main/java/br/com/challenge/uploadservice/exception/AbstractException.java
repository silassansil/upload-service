package br.com.challenge.uploadservice.exception;

import lombok.Getter;

import java.util.Collection;

@Getter
abstract class AbstractException extends RuntimeException {

    private Collection<String> details;

    AbstractException(String message, Collection<String> details) {
        super(message);
        this.details = details;
    }
}
