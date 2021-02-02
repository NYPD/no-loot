package rip.noloot.exception;

/**
 * Generic exception to be thrown whenever something goes wrong with API requests
 * 
 * @author NYPD
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 2795530368386310074L;

    public ApiException() {
        super();
    }

    public ApiException(Exception exception) {
        super(exception);
    }

}
