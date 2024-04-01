package com.jobenriquez.wetalk.registration;

/**
 * @author Isaiah Job Cuenca Enriquez
 */
public record RegistrationRequest (
     String username,
     String email,
     String firstName,
     String lastName,
     String password
) {

}
