package LabsProject.Mail;

import javax.mail.Authenticator;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    private MailService service;
    private String login;
    private String password;

    public SendMail(MailService ms, String login, String password) {
        this.service = ms;
        this.login = login;
        this.password = password;
    }
    public SendMail() {
        this.service = MailService.OUTLOOK;
        this.login  = "265087@niuitmo.ru";
        this.password = "wAd|eToDJfF}f@CI";
    }
    public void send(String txt, String adresat) {
        Properties prop = new Properties();

        prop.put("mail.smtp.host", service.getHost());
        prop.put("mail.smtp.port", service.getPort());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.port", service.getPort());
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(login));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(adresat));
            msg.setSubject("new password for lab 7");
            msg.setText(txt);
            Transport.send(msg);
        } catch (AuthenticationFailedException e) {
            System.err.println("Error: Invalid login or password!");
        } catch (MessagingException ee) {
            System.err.println("Error: something worng");
        }
    }
}
