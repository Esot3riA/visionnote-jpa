package org.swm.visionnotejpa.exception;

public class EmailDuplicatedException extends RuntimeException {
    public EmailDuplicatedException() {
        super();
    }

    public EmailDuplicatedException(String message) {
        super(message);
    }

    public EmailDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDuplicatedException(Throwable cause) {
        super(cause);
    }
}
