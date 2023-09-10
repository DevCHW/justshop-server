package com.justshop.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Component
public class GoogleMail implements MailSendClient {

    @Override
    public boolean sendMail(String fromEmail, String toEmail, String subject, String message) {
        // 1. 정보를 담기 위한 객체
        Properties prop = new Properties();

        // 2. SMTP(Simple Mail Transfer Protocoal) 서버의 계정 설정
        //    Google Gmail 과 연결할 경우 Gmail 의 email 주소를 지정
        prop.put("mail.smtp.user", fromEmail);

        // 3. SMTP 서버 정보 설정
        //    Google Gmail 인 경우  smtp.gmail.com
        prop.put("mail.smtp.host", "smtp.gmail.com");

        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");

        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Authenticator smtpAuth = new SMTPAuthenticator();
        Session ses = Session.getInstance(prop, smtpAuth);

        // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
        ses.setDebug(true);

        // 메일의 내용을 담기 위한 객체생성
        MimeMessage msg = new MimeMessage(ses);

        // 이메일 제목 넣기
        try {
            msg.setSubject(subject);

            Address fromAddr = new InternetAddress(fromEmail);
            msg.setFrom(fromAddr);

            // 받는 사람의 메일주소
            Address toAddr = new InternetAddress(toEmail);
            msg.addRecipient(Message.RecipientType.TO, toAddr);

            // 메시지 본문의 내용과 형식, 캐릭터 셋 설정
            msg.setContent(message, "text/html;charset=UTF-8");

            // 메일 발송하기
            Transport.send(msg);

        } catch (MessagingException e) {
            log.info("메일 전송 실패");
            return false;
        }

        return true;
    }

}
