package ru.geekbrains.coursework.webshop.app;

public class CallCurrentMethodException extends RuntimeException {
    public CallCurrentMethodException() {
        super();
    }

    public CallCurrentMethodException(String message) {
        super(message);
    }

    public CallCurrentMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public CallCurrentMethodException(Throwable cause) {
        super(cause);
    }

    protected CallCurrentMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
