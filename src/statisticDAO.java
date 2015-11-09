import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by magikarp on 16/05/2015.
 */
public class statisticDAO {

    private List<Float> results = new ArrayList<>();
    private sensorDAO dao = new sensorDAO();
    private Connection connection;
    private int[] latestData;

    public statisticDAO() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connection =  DriverManager.getConnection("jdbc:mysql://localhost/weathertracker",
                    "weatheruser", "1234");

        } catch (Exception e) {
            System.out.println(e);
            connection = null;
        }
    }


    public void updateStatistics(int tempValue, int humidValue, int lightValue) {

        HashMap<String, String> sensorStates = new HashMap<>();
        for (String sensor : dao.getSensorNameandState()) {
            sensorStates.put(sensor.split(",")[0],sensor.split(",")[1]);
        }


        HashMap<String, Float> means = new HashMap<>();
        HashMap<String, Float> sums = new HashMap<>();
        HashMap<String, Float> sds = new HashMap<>();

        if (sensorStates.get("temperature").equals("1")) {
            System.out.println("updating Temp data");
            updateSensorData("temperature", tempValue);
        }
        if (sensorStates.get("humidity").equals("1")) {
            System.out.println("updating humidity data");
            updateSensorData("humidity", humidValue);
        }
        if (sensorStates.get("light").equals("1")) {
            System.out.println("updating light data");
            updateSensorData("light", lightValue);

        }


        // calculates sums
        sums.put("temperature",getPrevStat("temperature", "sum") + tempValue);
        sums.put("humidity",getPrevStat("humidity", "sum") + humidValue);
        sums.put("light",getPrevStat("light", "sum") + lightValue);

        // calculates mean and sd
        means.put("temperature",calculateMean("temperature"));
        sds.put("temperature",calculateSd());
        means.put("humidity",calculateMean("humidity"));
        sds.put("humidity",calculateSd());
        means.put("light",calculateMean("light"));
        sds.put("light", calculateSd());
        Emaildao email = new Emaildao();
        NotificationDao notification = new NotificationDao();
        if (means.get("temperature") >= dao.getTolerences().get(0)) {
            String tempnote = "Temperature sensor has reached it's threshold!";
            email.sendEmail(tempnote);
            notification.addNotification(tempnote);
        }
        if (means.get("light") <= dao.getTolerences().get(2)) {
            String lightnote = "Light sensor has reached it's threshold!";
            email.sendEmail(lightnote);
            notification.addNotification(lightnote);
        }
        if (means.get("humidity") >= dao.getTolerences().get(1)) {
            String humidnote = "Humidity sensor has reached it's threshold!";
            email.sendEmail(humidnote);
            notification.addNotification("Humidity sensor has reached it's threshold!");
        }
        email.close();
        notification.close();

        dao.updateStats(sums, means, sds);
        try {
            connection.close();
            dao.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void updateSensorData(String sensor, int data) {

        try {
            switch (sensor) {
                case "temperature": {
                    String sql = "INSERT INTO temperature" + "(data) VALUES" + "(?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setFloat(1, data);
                    statement.executeUpdate();
                    statement.close();
                    break;
                }
                case "humidity": {
                    String sql = "INSERT INTO humidity" + "(data) VALUES" + "(?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setFloat(1, data);
                    statement.executeUpdate();
                    statement.close();
                    break;
                }
                default: {
                    String sql = "INSERT INTO light" + "(data) VALUES" + "(?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setFloat(1, data);
                    statement.executeUpdate();
                    statement.close();
                    break;
                }
            }
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public float calculateMean(String sensor) {
        float mean = 0;
        results.clear();
        ResultSet resultset = null;

        try {

            switch (sensor) {
                case "temperature": {
                    String sql = "SELECT data FROM temperature";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    resultset = statement.executeQuery();
                    break;
                }
                case "humidity": {
                    String sql = "SELECT data FROM humidity";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    resultset = statement.executeQuery();
                    break;
                }
                default: {
                    String sql = "SELECT data FROM light";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    resultset = statement.executeQuery();
                    break;
                }
            }


            while (resultset.next()) {
                results.add(resultset.getFloat("data"));
            }


            if (!results.isEmpty()) {
                for (float result : results) {
                    mean += result;
                }
              mean = mean / results.size();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }


        return mean;
    }

    public float getPrevStat(String sensor, String stat) {
        float temp = 0;
        try {

            String sql = "SELECT *  FROM sensors WHERE sensor =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, sensor);
            ResultSet resultset = statement.executeQuery();
            while (resultset.next()) {

                temp = resultset.getFloat(stat);

            }
          //  connection.close();
        } catch (SQLException e) {
            System.out.println(e);

        }

        return temp;

    } //re-do

    public  float calculateSd() {
        double sd = 0;
        float mean = 0;
        float meansq = 0;
        List<Float> resultssq = new ArrayList<>();

        // calculates mean
        if (!results.isEmpty()) {
            for (float result : results) {
                mean += result;
            }
            mean = mean / results.size();
        }


        // calcualtes the square difference
        if (!results.isEmpty()) {
            for (float result : results) {
                resultssq.add(((result - mean)*(result - mean)));
            }
        }

        // calculates the mean of the square differences
        if (!results.isEmpty()) {
            for (float result : resultssq) {
                meansq += result;
            }
            meansq = meansq / results.size();
        }

        // square root of the mean of the square differences

            sd = Math.sqrt(meansq);

        return (float) sd;
    }


    public int[] getLatestData() {
        int[] latestData = new int[3];
        try {
            String sql = "SELECT data FROM temperature ORDER BY record DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultset = statement.executeQuery();
            if (resultset.next()) {
                latestData[0] = resultset.getInt("data");
                statement.close();

            }
            sql = "SELECT data FROM humidity ORDER BY record DESC LIMIT 1";
            statement = connection.prepareStatement(sql);
            resultset = statement.executeQuery();
            if (resultset.next()) {
                latestData[1] = resultset.getInt("data");
            }
            sql = "SELECT data FROM light ORDER BY record DESC LIMIT 1";
            statement = connection.prepareStatement(sql);
            resultset = statement.executeQuery();
            if (resultset.next()) {
                latestData[2] = resultset.getInt("data");
            }
         //   connection.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return latestData;
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
