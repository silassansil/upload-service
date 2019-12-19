package br.com.challenge.uploadservice.exception.resource;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class ErrorDetailsResource {

    public ErrorDetailsResource(String message, Collection<String> details) {
        this.message = message;
        this.details = details;
    }

    private String message;
    private Collection<String> details;

    public String getMessage() {
        return message;
    }

    public Collection<String> getDetails() {
        return details;
    }
}
