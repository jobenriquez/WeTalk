package com.jobenriquez.wetalk.event.listener;

/**
 * @author Isaiah Job Cuenca Enriquez
 */

import com.jobenriquez.wetalk.event.RegistrationCompleteEvent;
import com.jobenriquez.wetalk.user.User;
import com.jobenriquez.wetalk.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private User newUser;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. Get the newly registered user
        newUser = event.getUser();
        // 2. Create a verification token
        String verificationToken = UUID.randomUUID().toString();
        // 3. Save the verification token
        userService.saveUserVerificationToken(newUser, verificationToken);
        // 4. Build the verification url to be sent
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "WeTalk Email Verification";
        String senderName = "WeTalk";
        String mailContent = "<p> Hi, "+ newUser.getFirstName()+ ", </p>"+
                "<p>Thank you for your interest in creating an account for WeTalk."+" " +
                "To finish the registration, please click on the link below to activate your account.</p>"+
                "<a href=\"" +url+ "\">Verify your email</a>"+
                "<p> Thank you, <br> WeTalk Team";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("dailycodework@gmail.com", senderName);
        messageHelper.setTo(newUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}