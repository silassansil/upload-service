package br.com.challenge.uploadservice.exception.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@JsonInclude(NON_NULL)
public class ErrorDetailsResource {

    public ErrorDetailsResource(String message, Collection<String> details) {
        this.message = message;
        this.details = details;
    }

    private String message;
    private Collection<String> details;
}
