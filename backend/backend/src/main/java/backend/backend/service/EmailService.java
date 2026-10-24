
package backend.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.backend-url:http://localhost:8080}")
    private String backendUrl;

    public void sendVerificationEmail(
            String toEmail,
            String token) {

        String verificationUrl = backendUrl
                + "/api/auth/verify-email?token="
                + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Xac thuc tai khoan dat ve xe");

        message.setText(
                "Xin chao!\n\n"
                        + "Vui long nhan vao link sau de xac thuc email:\n"
                        + verificationUrl
                        + "\n\nLink co hieu luc trong 15 phut.");

        mailSender.send(message);
    }
}
