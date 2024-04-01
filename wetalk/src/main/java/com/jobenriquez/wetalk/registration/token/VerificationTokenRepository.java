package com.jobenriquez.wetalk.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Isaiah Job Cuenca Enriquez
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
}
