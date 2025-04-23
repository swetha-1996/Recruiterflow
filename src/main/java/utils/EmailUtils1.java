package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import config.ConfigReader;

import static java.lang.System.getenv;

public class EmailUtils1 {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtils1.class);

    public static void sendReport(String reportPath) {
        final String username = getenv("EMAIL_USER");
        final String password = getenv("EMAIL_PASS");
        String toEmail = "testtd77@gmail.com";


        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            logger.error("Email credentials are not set in the environment variables.");
            return;
        }
        // Validate the report file
        File reportFile = new File(reportPath);
        if (!reportFile.exists() || !reportFile.isFile()) {
            logger.error("Report file does not exist or is invalid: {}", reportPath);
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",ConfigReader.get("smtp.host"));
        props.put("mail.smtp.port",ConfigReader.get("smtp.port"));
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // Enable debug mode for SMTP
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Automation Test Report");
            // Email body with a link to the ExtentReport
            String reportLink = reportPath; // Replace with the actual link
            String htmlContent = "<p>Please find the attached automation report.</p>" +
                    "<p>You can also view the report online: <a href='" + reportLink + "'>ExtentReport</a></p>";

            // Set the email content as HTML
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(reportFile);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);

            logger.info("Email sent successfully with attachment and report link: {}", reportPath);


        } catch (MessagingException e) {
            logger.error("Failed to send email due to messaging error: {}", e.getMessage(), e);
       // } catch (IOException e) {
            //logger.error("Failed to attach the report file: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
        }
    }
}