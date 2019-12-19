package br.com.challenge.uploadservice.exception;

import static java.lang.String.format;
import static java.util.Collections.singleton;

public class InvalidImageTypeException extends AbstractException {

    public InvalidImageTypeException(String type) {
        super("invalid file type", singleton(format("invalid file type %s", type)));
    }
}
