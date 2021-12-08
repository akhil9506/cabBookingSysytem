package com.akhil.cabBookingSystem.exception;

public class RoleMisMatchException extends Exception {
    public RoleMisMatchException() {
        super();
    }

    public RoleMisMatchException(String s) {
        super(s);
    }

    public RoleMisMatchException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RoleMisMatchException(Throwable throwable) {
        super(throwable);
    }

    protected RoleMisMatchException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
