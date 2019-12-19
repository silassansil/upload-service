package br.com.challenge.uploadservice.exception.handle;

import br.com.challenge.uploadservice.exception.InvalidImageTypeException;
import br.com.challenge.uploadservice.exception.ResourceNotFoundException;
import br.com.challenge.uploadservice.exception.resource.ErrorDetailsResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import static java.util.Collections.singleton;

@ControllerAdvice
public class HandlerExceptionResource {

    private static final String MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION = "Max Upload Size Exceeded Exception";
    private static final String LIMIT_ALLOWED_FOR_THIS_OPERATION = "Limit allowed for this operation in 5MB";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsResource> handler(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetailsResource(ex.getMessage(), ex.getDetails()));
    }

    @ExceptionHandler(InvalidImageTypeException.class)
    public ResponseEntity<ErrorDetailsResource> handler(InvalidImageTypeException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorDetailsResource(ex.getMessage(), ex.getDetails()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDetailsResource> handler(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetailsResource(MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION, singleton(LIMIT_ALLOWED_FOR_THIS_OPERATION)));
    }
}
