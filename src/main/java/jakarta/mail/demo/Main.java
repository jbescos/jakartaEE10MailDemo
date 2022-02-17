package jakarta.mail.demo;

import java.util.Properties;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class Main {

    private static final Properties MAIL_PROPERTIES = new Properties();

    static {
        MAIL_PROPERTIES.setProperty("mail.smtp.host", "0.0.0.0");
        MAIL_PROPERTIES.setProperty("mail.smtp.port", "18000");
    }

    public static void main(String[] args) throws AddressException, MessagingException {
        Session session = Session.getDefaultInstance(MAIL_PROPERTIES);
        session.setDebug(true);
        sendSmtpMail(session, "from@demo.com", "to@demo.com");
    }

    private static void sendSmtpMail(Session session, String from, String to) throws AddressException, MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Hello Demo!");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("This is the body of the message");
        message.setContent(new MimeMultipart(messageBodyPart));
        Transport.send(message);
    }
}
