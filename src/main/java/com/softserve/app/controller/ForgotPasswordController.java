package com.softserve.app.controller;

import com.softserve.app.models.User;
import com.softserve.app.request.EmailRequest;
import com.softserve.app.request.ResetPasswordRequest;
import com.softserve.app.service.userService.UserServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/forgot_password")
    public ResponseEntity<?> processForgotPassword(@RequestBody EmailRequest emailRequest, HttpServletRequest request, Model model) {
        String email = emailRequest.getEmail();
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:3000/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
        } catch (UnsupportedEncodingException | MessagingException e) {

            return new ResponseEntity<>("Failed to send an email", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    ///кидає лист на пошту з ссилкою на фронт сторінку з токеном ресету пароля
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("SportHub@gmail.com", "SportHub support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public ResponseEntity<String> showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User customer = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (customer == null) {
            return new ResponseEntity<>("ERROR", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Everything is ok", HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> processResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();

        String newPassword = resetPasswordRequest.getNewPassword();
        String confirmPassword = resetPasswordRequest.getConfirmPassword();
        if (confirmPassword.equals(newPassword) && userService.getByResetPasswordToken(token) != null) {
            User user = userService.getByResetPasswordToken(token);
            if (user != null) {
                userService.updatePassword(user, newPassword);
                System.out.println(user.getPassword());
            }
        } else {
            return new ResponseEntity<>("Confirm pass != new pass or invalid token", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
}