package jakarta.mail.demo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Main {

    private static final Properties MAIL_PROPERTIES = new Properties();

    static {
        MAIL_PROPERTIES.setProperty("mail.smtp.host", "0.0.0.0");
        MAIL_PROPERTIES.setProperty("mail.smtp.port", "18000");
        MAIL_PROPERTIES.setProperty("mail.imap.host", "0.0.0.0");
        MAIL_PROPERTIES.setProperty("mail.imap.port", "18004");
        MAIL_PROPERTIES.setProperty("mail.pop3.host", "0.0.0.0");
        MAIL_PROPERTIES.setProperty("mail.pop3.port", "18007");
//        MAIL_PROPERTIES.setProperty("mail.pop3.disablecapa", "true");
//        MAIL_PROPERTIES.setProperty("mail.pop3.disabletop", "false");
//        MAIL_PROPERTIES.setProperty("mail.pop3.forgettopheaders", "false");
        MAIL_PROPERTIES.setProperty("mail.pop3.auth", "false");
//        MAIL_PROPERTIES.setProperty("mail.debug", "true");
    }

    public static void main(String[] args) throws AddressException, MessagingException, IOException, InterruptedException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Need one argument with the body message");
        }
        Session session = Session.getDefaultInstance(MAIL_PROPERTIES);
        sendEmail(session, "user01@james.local", "user02@james.local", args[0]);
        Thread.sleep(1000L);
        readEmails(session, "user02@james.local", "imap");
        readEmails(session, "user02@james.local", "pop3");
    }

    private static void sendEmail(Session session, String from, String to, String text) throws AddressException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Hello Demo!");
        message.setText(text);
        Transport.send(message);
        System.out.println("Email sent from " + from + " to " + to);
    }

    private static void readEmails(Session session, String userMail, String protocol) throws MessagingException, IOException {
        try (Store store = session.getStore(protocol)) {
            store.connect(userMail, "1234");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            System.out.println(protocol + " -> Message count: " + folder.getMessageCount());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Message message : folder.getMessages()) {
                System.out.print(format.format(message.getSentDate()) 
                        + " | Subject: " + message.getSubject() 
                        + " | From: " + Arrays.asList(message.getFrom()) 
                        + " | Recipients: " + Arrays.asList(message.getAllRecipients())
                        + " | Content: " + message.getContent());
            }
            folder.close();
        }
    }
}
