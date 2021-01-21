package rip.noloot.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * Runtime exception to be thrown whenever the state token on oAuth implementations comes back different from the original
 * state token saved to the session. Meaning someone be hacking up in here.
 * 
 * @author NYPD
 *
 */
public class InvalidStateTokenException extends RuntimeException {

    private static final long serialVersionUID = -1466713577883122972L;
    private final String ipAddress;

    public InvalidStateTokenException(HttpServletRequest request) {
        super();

        String forwardedForHeader = request.getHeader("X-FORWARDED-FOR");
        this.ipAddress = forwardedForHeader != null ? forwardedForHeader : request.getRemoteAddr();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return "InvalidStateTokenException [ipAddress=" + ipAddress + "]";
    }

}