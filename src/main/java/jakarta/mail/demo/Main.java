package jakarta.mail.demo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
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

    public static void main(String[] args) throws AddressException, MessagingException, IOException, InterruptedException {
        Session session = Session.getDefaultInstance(MAIL_PROPERTIES);
//        session.setDebug(true);
        sendEmail(session, "user01@james.local", "user02@james.local");
        Thread.sleep(1000L);
        readEmails(session, "user02@james.local");
    }

    private static void sendEmail(Session session, String from, String to) throws AddressException, MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Hello Demo!");
        message.setText("This is the body of the message");
        Transport.send(message);
        System.out.println("Email sent from " + from + " to " + to);
    }

    private static void readEmails(Session session, String userMail) throws MessagingException, IOException {
        String folderName = "INBOX";
        try (Store store = session.getStore("imap")) {
            store.connect("0.0.0.0", 18004, userMail, "1234");
            System.out.println(folderName + " of " + userMail);
            Folder folder = store.getFolder(folderName);
            folder.open(Folder.READ_WRITE);
            System.out.println("Message count: " + folder.getMessageCount());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Message message : folder.getMessages()) {
                System.out.println(format.format(message.getSentDate()) 
                        + " | Subject: " + message.getSubject() 
                        + " | From: " + Arrays.asList(message.getFrom()) 
                        + " | Recipients: " + Arrays.asList(message.getAllRecipients())
                        + " | Content: " + message.getContent());
            }
        }
    }
}
