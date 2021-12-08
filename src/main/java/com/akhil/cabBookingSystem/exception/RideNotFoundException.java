package com.akhil.cabBookingSystem.exception;

public class RideNotFoundException extends Exception{
    public RideNotFoundException() {
        super();
    }

    public RideNotFoundException(String s) {
        super(s);
    }

    public RideNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RideNotFoundException(Throwable throwable) {
        super(throwable);
    }

    protected RideNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
