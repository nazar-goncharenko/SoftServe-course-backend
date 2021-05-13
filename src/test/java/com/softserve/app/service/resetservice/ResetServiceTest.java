package com.softserve.app.service.resetservice;

import com.softserve.app.models.ResetPasswordRequest;
import com.softserve.app.models.User;
import com.softserve.app.service.resetService.ResetService;
import com.softserve.app.service.userService.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ResetServiceTest {

    private static final String TEST_PASSWORD = "TestPassword";
    private static final String TEST_TOKEN = "TestToken";

    @InjectMocks
    private ResetService resetService;

    @Mock
    private UserService userService;

    @Test
    public void resetPasswordTest() {
        when(userService.getByResetPasswordToken(TEST_TOKEN)).thenReturn(new User());

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setNewPassword(TEST_PASSWORD);
        resetPasswordRequest.setConfirmPassword(TEST_PASSWORD);
        resetPasswordRequest.setToken(TEST_TOKEN);
        resetService.resetPassword(resetPasswordRequest);

    }
}
