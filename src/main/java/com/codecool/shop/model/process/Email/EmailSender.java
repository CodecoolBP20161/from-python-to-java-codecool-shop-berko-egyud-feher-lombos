package com.codecool.shop.model.process.Email;


import com.codecool.shop.model.process.AbstractProcess;
import com.codecool.shop.services.ConnectionPropertyValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Properties;


public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    static ConnectionPropertyValues configReader = new ConnectionPropertyValues();
    static HashMap EmailProperties = configReader.getPropValuesOfEmail();

    public static String SENDER_EMAIL = EmailProperties.get("sender_email").toString();
    public static String SENDER_PASSWORD = EmailProperties.get("sender_password").toString();
    public static String HOST = EmailProperties.get("host").toString();
    public static String SMTP_HOST = "smtp.gmail.com";
    public static String SMTP_PORT = "587";


    public static void send(String userEmail, String subject, String messagetext) {

        try {
            LOGGER.info("EmailSender.send() method is called.");
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST); //SMTP Host
            props.put("mail.smtp.port", SMTP_PORT); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            };
            Session session = Session.getInstance(props, auth);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

            message.setSubject(subject);
            message.setText(messagetext);

            Transport.send(message);
            LOGGER.info("Email sent.");

        } catch (Exception ex) {
            LOGGER.error("EmailSender.send() method failed: " +  ex);
        }
    }
}
