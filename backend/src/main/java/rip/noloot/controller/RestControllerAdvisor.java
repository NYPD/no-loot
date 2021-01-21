package rip.noloot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import rip.noloot.exception.InvalidStateTokenException;

/**
 * {@link ControllerAdvice} to be used for a {@link RestController}s defined in this application
 * 
 * @author NYPD
 *
 */
@RestControllerAdvice(annotations = RestController.class)
public class RestControllerAdvisor {

    private static final Logger LOGGER = LogManager.getLogger(RestControllerAdvisor.class);

    @ExceptionHandler(value = InvalidStateTokenException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void handleFileReadException(InvalidStateTokenException exception) {

        LOGGER.info("User not found in session for AJAX call, redirecting to login page ", exception);

    }
}
