package com.jobenriquez.wetalk.exception;

/**
 * @author Isaiah Job Cuenca Enriquez
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
