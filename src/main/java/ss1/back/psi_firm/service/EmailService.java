package ss1.back.psi_firm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ss1.back.psi_firm.utils.AuthUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final AuthUtils authUtils;

    public String sendVerificationCode(String email) {
        String verificationCode = authUtils.generateVerificationCode();
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Codigo de verificacion");
        message.setText("El código para iniciar sesión es: " + verificationCode);
        
        javaMailSender.send(message);
        log.info("Código de verificación enviado a: {}", email);

        return verificationCode;
    }

}
