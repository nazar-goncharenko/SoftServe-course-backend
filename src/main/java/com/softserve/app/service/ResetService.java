package com.softserve.app.service;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.models.ResetPasswordRequest;
import com.softserve.app.service.userService.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class ResetService {

    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;

    public void createToken(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:4200/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new SportHubException(SportHubConstant.EMAIL_SENDING_EXCEPTION.getMessage(), 405);
        }
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();

        String newPassword = resetPasswordRequest.getNewPassword();
        String confirmPassword = resetPasswordRequest.getConfirmPassword();
        if (confirmPassword.equals(newPassword) && userService.getByResetPasswordToken(token) != null) {
            User user = userService.getByResetPasswordToken(token);
            if (user != null) {
                userService.updatePassword(user, newPassword);
            }
        } else {
            throw  new SportHubException(SportHubConstant.PASSWORD_RESET_FAILED.getMessage(), 404);
        }
        new ResponseEntity<>(SportHubConstant.PASSWORD_RESTORE_MESSAGE.getMessage(), HttpStatus.OK);
    }

    ///кидає лист на пошту з ссилкою на фронт сторінку з токеном ресету пароля
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(SportHubConstant.COMPANY_EMAIL.getMessage(), SportHubConstant.COMPANY_NAME.getMessage());
        helper.setTo(recipientEmail);

        String subject = SportHubConstant.EMAIL_TITLE.getMessage();
        String content = SportHubConstant.EMAIL_TEXT_P1.getMessage() +
                link +
                SportHubConstant.EMAIL_TEXT_P2.getMessage();

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
