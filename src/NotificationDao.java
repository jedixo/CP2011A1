import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by magikarp on 22/05/2015.
 */
public class NotificationDao {

    private Connection connection;

    public NotificationDao() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/weathertracker",
                    "weatheruser", "1234");

        } catch (Exception e) {
            System.out.println(e);
            connection = null;
        }

    }

    public void delete(int id) {
        try {
            String sql = "DELETE FROM notifications WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (Exception e){
            System.out.println(e);
        }
        
    }
    public void addNotification(String notification) {
        try {
            String sql = "INSERT INTO notifications (notification) VALUES" + "(?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, notification);
            statement.executeUpdate();

        } catch (Exception e){
            System.out.println(e);
        }
        
    }

    public List<String> getNotifications() {
        List<String> notifications = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT *  FROM notifications";
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                String notification = resultset.getString("notification");
                String id = resultset.getString("id");
                notifications.add(notification + "," + id);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return notifications;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
