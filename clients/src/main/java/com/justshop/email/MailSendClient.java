package com.justshop.email;

public interface MailSendClient {
    boolean sendMail(String fromEmail, String toEmail, String subject, String message);

}
