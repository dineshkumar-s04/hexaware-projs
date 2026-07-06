package com.hexaware.careassist.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.entity.PasswordResetToken;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.repository.PasswordResetTokenRepository;
import com.hexaware.careassist.repository.UserRepository;
import com.hexaware.careassist.service.IPasswordResetService;

@Service
public class PasswordResetServiceImpl implements IPasswordResetService {

    private static final Logger logger =
            LoggerFactory.getLogger(PasswordResetServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public String forgotPassword(String email) {

        logger.info("Forgot password requested for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryTime(LocalDateTime.now().plusMinutes(30));
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("CareAssist Password Reset");

        String resetLink =
                "http://localhost:5173/reset-password?token=" + token;

        message.setText(
                "Hello " + user.getName() + ",\n\n"
                + "Click the link below to reset your password:\n\n"
                + resetLink
                + "\n\nThis link will expire in 30 minutes.");

        mailSender.send(message);

        logger.info("Password reset email sent to {}", email);

        return "Password reset email sent successfully.";
    }
    
    @Override
    public String resetPassword(String token, String newPassword) {

        logger.info("Reset password request received.");

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid password reset token."));

        if (resetToken.isUsed()) {
            throw new RuntimeException("Password reset token has already been used.");
        }

        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token has expired.");
        }

        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        resetToken.setUsed(true);

        tokenRepository.save(resetToken);

        logger.info("Password updated successfully for user: {}", user.getEmail());

        return "Password reset successfully.";
    }

}