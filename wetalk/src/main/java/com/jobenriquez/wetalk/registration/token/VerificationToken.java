package com.jobenriquez.wetalk.registration.token;

import com.jobenriquez.wetalk.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Isaiah Job Cuenca Enriquez
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    private static final int EXPIRATION_TIME_ALLOWANCE = 20;


    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(calendar.MINUTE, EXPIRATION_TIME_ALLOWANCE);
        return new Date(calendar.getTime().getTime());
    }
}


