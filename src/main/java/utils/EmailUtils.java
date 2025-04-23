package utils;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.File;
public class EmailUtils {

        public static void sendReport(String reportPath) {
            final String username = "testtd77@gmail.com";
            final String password = "iso*help123";
            String toEmail = "testtd77@gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com"); // or your SMTP
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(
                        Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject("Automation Test Report");

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText("Please find the attached automation report.");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // Attachment
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(new File(reportPath));
                multipart.addBodyPart(attachmentPart);

                message.setContent(multipart);

                Transport.send(message);
                System.out.println("Email Sent Successfully with attachment");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
