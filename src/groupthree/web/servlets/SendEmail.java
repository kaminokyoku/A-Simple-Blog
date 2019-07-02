package groupthree.web.servlets;
//https://www.google.com/settings/security/lesssecuresapps

import util.DBConnectionUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;


public class SendEmail {
    static User user = new User();
    private static String uniqueTokenString = user.randomAlphaNumeric();

    public static void sendingNewUserEmailAndPassword(String to, String NewuserName, String NewUserPassword) {

        try {
            String host = "smtp.gmail.com";
            String user = "g1628928321@gmail.com"; // admin email address
            String pass = "legolas060707"; // type your email password here.
            String from = "g1628928321@gmail.com";// admin email address
            String subject = "Your UserName and Password for our website";
            String messageText = "Hi How are you today, These is your " +
                    "\n Username: "
                    + NewuserName + "\n and Your Password" + NewUserPassword + "\n" + "for our webpage \n"
                    + "\n Have a nice Day ! \n Thank you :)";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            // we need security so java already provide security.
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)}; // address of sender
            msg.setRecipients(Message.RecipientType.TO, address); // recover email
            msg.setSubject(subject);
            msg.setSentDate(new Date()); // message send date
            msg.setText(messageText);  // actual message

            Transport transport = mailSession.getTransport("smtp"); // server through which are going to send message
            transport.connect(host, user, pass); // we need because we have to authentic sender email and password
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public static void sendingUsertoPasswordRecoveryPage(String to, int id) {

        String link = " <a href='https://sporadic.nz/group3_2019_group3/Admin/ChangePasswordPage.jsp?token=" + uniqueTokenString + "'>" + uniqueTokenString + "</a><br>";
        System.out.println(link);
        try {
            String host = "smtp.gmail.com";
            String user = "g1628928321@gmail.com"; // admin email address
            String pass = "legolas060707";// type your email password here.

            String from = "g1628928321@gmail.com"; // admin email address
            String subject = "Password Recovery";
            String messageText = "Hi there!,\n Here is a link to change your password \n please click the link and do as follow \n \n \n \n" +
                    link +
                    "Thank you \n" +
                    "have a nice Day " +
                    "Yours ;)";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            // we need security so java already provide security.
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)}; // address of sender
            msg.setRecipients(Message.RecipientType.TO, address); // recover email
            msg.setSubject(subject);
            msg.setSentDate(new Date()); // message send date
            msg.setContent(messageText, "text/html");  // actual message

            Transport transport = mailSession.getTransport("smtp"); // server through which are going to send message
            transport.connect(host, user, pass); // we need because we have to authentic sender email and password
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");


        } catch (Exception ex) {
            System.out.println(ex);
        }
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            System.out.println("token to database: " + uniqueTokenString);
            System.out.println(user.getUserId());

            UserDAO.insertToken(id, uniqueTokenString, conn);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
