package com.justshop.apiserver.api.user.mail;

import com.justshop.email.MailSendClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;

    // 메일 전송
    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendMail(fromEmail, toEmail, subject, content);

        if (result) {
            return true;
        }

        return false;
    }

}
