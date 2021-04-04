package com.eestienergia.fundraiser.rest.exception;


import com.eestienergia.fundraiser.domain.exception.ProductNotFoundException;
import com.eestienergia.fundraiser.domain.exception.ProductOutOfStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<RestExceptionResource> handle(final Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(RestExceptionResource.builder().message("Something went wrong.").build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<RestExceptionResource> handle(final NoHandlerFoundException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(RestExceptionResource.builder().message("Not found.").build());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<RestExceptionResource> handle(final ProductNotFoundException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(RestExceptionResource.builder().message("Product not found.").build());
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    protected ResponseEntity<RestExceptionResource> handle(final ProductOutOfStockException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(RestExceptionResource.builder().message(exception.getMessage()).build());
    }
}