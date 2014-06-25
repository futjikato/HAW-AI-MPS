package de.haw.mps.transport;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps.transport
 */
public class UppsException extends Exception {
    public UppsException() {
    }

    public UppsException(String message) {
        super(message);
    }

    public UppsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UppsException(Throwable cause) {
        super(cause);
    }

    public UppsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
