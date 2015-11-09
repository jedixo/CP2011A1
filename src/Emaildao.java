import com.sun.jndi.cosnaming.IiopUrl;

import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by magikarp on 21/05/2015.
 */
public class Emaildao {

    private Connection connection;

    public Emaildao() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/weathertracker",
                    "weatheruser", "1234");

        } catch (Exception e) {
            System.out.println(e);
            connection = null;
        }

    }

    public void addEmail(String address) {
        try {
            String sql = "INSERT INTO emails (email) VALUES" + "(?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, address);
            statement.executeUpdate();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void delete(int id) {
        try {
            String sql = "DELETE FROM emails WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    public List<String> getEmail() {
        List<String> emails = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT *  FROM emails";
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                String email = resultset.getString("email");
                String id = resultset.getString("id");
                emails.add(email + "," + id);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return emails;
    }

    public void sendEmail(String messagestr) {
        List<String> addresses = new ArrayList<>();
        //int x = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT *  FROM emails";
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                String email = resultset.getString("email");
                addresses.add(email);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        for (int i = 0; i < 3; ++i) {

            String from = "jake.dixon@my.jcu.edu.a";
            String host = "localhost";
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.user", "jak3dix0n@gmail.com");
            properties.setProperty("mail.password", "fordsrock1");
            Session session = Session.getDefaultInstance(properties);
            //response.SetContentType("text/html");

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(addresses.get(i)));
                message.setSubject("sensor Threshold Reached");
                message.setText(messagestr);
                Transport.send(message);
                System.out.println("mesage sent");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public void close() {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
