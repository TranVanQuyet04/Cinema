package com.cinemaai.identity.service;

import com.cinemaai.identity.config.MailProperties;
import com.cinemaai.identity.exception.BadRequestException;
import com.cinemaai.identity.repository.UserRepository;
import com.cinemaai.identity.service.impl.MailServiceImpl;
import com.cinemaai.identity.service.impl.UserServiceImpl;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OtpDeliveryTest {
    @Test
    void disabledMailMustReportFailure() {
        MailProperties properties = new MailProperties();
        properties.setEnabled(false);
        ObjectProvider<JavaMailSender> provider = mock(ObjectProvider.class);
        MailServiceImpl service = new MailServiceImpl(provider, properties);
        assertThrows(RuntimeException.class, () -> service.sendOtp("person@example.org", "123456", "verification"));
    }

    @Test
    void smtpFailureMustReportFailure() {
        MailProperties properties = new MailProperties();
        properties.setEnabled(true);
        properties.setFrom("sender@example.org");
        JavaMailSender sender = mock(JavaMailSender.class);
        ObjectProvider<JavaMailSender> provider = mock(ObjectProvider.class);
        when(provider.getIfAvailable()).thenReturn(sender);
        when(sender.createMimeMessage()).thenReturn(new MimeMessage(Session.getInstance(new Properties())));
        doThrow(new MailSendException("delivery failed")).when(sender).send(any(MimeMessage.class));
        MailServiceImpl service = new MailServiceImpl(provider, properties);
        assertThrows(RuntimeException.class, () -> service.sendOtp("person@example.org", "123456", "verification"));
    }

    @Test
    void successfulProvisionDeliveryCreatesVerifiableOtp() {
        UserRepository repository = mock(UserRepository.class);
        MailService mail = mock(MailService.class);
        UserServiceImpl service = new UserServiceImpl(repository, null, null, null, null, null, null, null, mail);
        service.sendProvisioningOtp("person@example.org");
        org.mockito.ArgumentCaptor<String> otp = org.mockito.ArgumentCaptor.forClass(String.class);
        verify(mail).sendOtp(eq("person@example.org"), otp.capture(), anyString());
        assertTrue(service.verifyProvisioningOtp("person@example.org", otp.getValue()));
    }

    @Test
    void failedProvisionDeliveryDoesNotProduceAUsableOtp() {
        UserRepository repository = mock(UserRepository.class);
        MailService mail = mock(MailService.class);
        UserServiceImpl service = new UserServiceImpl(repository, null, null, null, null, null, null, null, mail);
        org.mockito.ArgumentCaptor<String> otp = org.mockito.ArgumentCaptor.forClass(String.class);
        doThrow(new IllegalStateException("unavailable")).when(mail).sendOtp(anyString(), otp.capture(), anyString());
        assertThrows(IllegalStateException.class, () -> service.sendProvisioningOtp("person@example.org"));
        assertThrows(BadRequestException.class, () -> service.verifyProvisioningOtp("person@example.org", otp.getValue()));
    }
}
