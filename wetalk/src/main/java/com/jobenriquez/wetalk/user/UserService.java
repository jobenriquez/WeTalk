package com.jobenriquez.wetalk.user;

/**
 * @author Isaiah Job Cuenca Enriquez
 */

import com.jobenriquez.wetalk.exception.UserAlreadyExistsException;
import com.jobenriquez.wetalk.registration.RegistrationRequest;
import com.jobenriquez.wetalk.registration.token.VerificationToken;
import com.jobenriquez.wetalk.registration.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> userEmail = this.findByEmail(request.email());
        Optional<User> userUsername = this.findByUsername(request.username());
        if (userUsername.isPresent()) {
            throw new UserAlreadyExistsException("The username" + request.username() + " is already taken.");
        }
        if (userEmail.isPresent()) {
            throw new UserAlreadyExistsException("The email" + request.email() + " is already in use.");
        }
        var newUser = new User();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setUsername(request.username());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password())); // Bcrypt encoder
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUserVerificationToken(User newUser, String token) {
        var verificationToken = new VerificationToken(token, newUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null) {
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            //tokenRepository.delete(token);
            return "Expired verification token";
        }
        user.setActive(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var tokenExpirationTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }
}
