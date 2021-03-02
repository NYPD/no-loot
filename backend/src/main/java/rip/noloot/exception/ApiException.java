package rip.noloot.exception;

import org.springframework.http.HttpStatus;

/**
 * Generic exception to be thrown whenever something goes wrong with API requests
 * 
 * @author NYPD
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 2795530368386310074L;
    private final HttpStatus statusCode;
    private final String errorMessage;

    public ApiException(String errorMessage) {
        this(null, null, errorMessage);
    }

    public ApiException(Exception exception) {
        this(exception, null, null);
    }

    public ApiException(HttpStatus statusCode, String errorMessage) {
        this(null, statusCode, errorMessage);
    }

    public ApiException(Exception exception, HttpStatus statusCode, String errorMessage) {
        super(exception);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    /* DEFAULT ACCESORS */
    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ApiException [statusCode=" + this.statusCode + ", errorMessage=" + this.errorMessage + "]";
    }

}
