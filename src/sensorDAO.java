import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by magikarp on 14/05/2015.
 */
public class sensorDAO {

    private Connection connection;
    private int[][] statistics;
    private List<Integer> tolerences;

    public sensorDAO() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/weathertracker",
                    "weatheruser","1234");

        } catch (Exception e) {
            System.out.println(e);
            connection = null;
        }
    }



    public List<String> getSensorNameandState() {
        List<String> results = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT sensor,state  FROM sensors";
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                String sensor = resultset.getString("sensor");
                String state = resultset.getString("state");
                results.add(sensor + "," + state);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return results;
    }

    public void updateState(int tempstate, int humidstate, int lightstate) {

        HashMap<String, Integer> sensorstates = new HashMap<>();
        sensorstates.put("temperature",tempstate);
        sensorstates.put("humidity",humidstate);
        sensorstates.put("light",lightstate);

        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT sensor,state  FROM sensors";
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                String sensor = resultset.getString("sensor");
                int state = resultset.getInt("state");
                if (state == 0 && sensorstates.get(sensor) == 1 ) {
                    String sql1 = "UPDATE sensors SET state=?, sum=?, mean=?, sd=? WHERE sensor=?";
                    PreparedStatement statement1 = connection.prepareStatement(sql1);
                    statement1.setBoolean(1, true);
                    statement1.setFloat(2, 0);
                    statement1.setFloat(3, 0);
                    statement1.setFloat(4, 0);
                    statement1.setString(5, sensor);
                    statement1.executeUpdate();

                } else if (state == 1 && sensorstates.get(sensor) == 0) {
                    String sql1 = "UPDATE sensors SET state=? WHERE sensor=?";
                    PreparedStatement statement1 = connection.prepareStatement(sql1);
                    statement1.setBoolean(1, false);
                    statement1.setString(2, sensor);
                    statement1.executeUpdate();
                }
            }
          //  connection.close();


        } catch (Exception e) {
            System.out.println(e);
        }




    }


    public void updateStats(HashMap<String, Float> sum, HashMap<String, Float> mean, HashMap<String, Float> sd) {


        HashMap<String, String> sensorStates = new HashMap<>();
        for (String sensor : getSensorNameandState()) {
            sensorStates.put(sensor.split(",")[0],sensor.split(",")[1]);
        }

        float tempsum = sum.get("temperature");
        float tempmean = mean.get("temperature");
        float tempsd = sd.get("temperature");

        float humidsum = sum.get("humidity");
        float humidmean = mean.get("humidity");
        float humidsd = sd.get("humidity");

        float lightsum = sum.get("light");
        float lightmean = mean.get("light");
        float lightsd = sd.get("light");

        String sql1;

        try {
            if (sensorStates.get("temperature").equals("1")) {
                System.out.println("updating Temp Statistics");
                sql1 = "UPDATE sensors SET sum=?, mean=?, sd=? WHERE sensor=?";
                PreparedStatement statement = connection.prepareStatement(sql1);
                statement.setFloat(1, tempsum);
                statement.setFloat(2, tempmean);
                statement.setFloat(3, tempsd);
                statement.setString(4, "temperature");
                statement.executeUpdate();
            }
            if (sensorStates.get("humidity").equals("1")) {
                System.out.println("updating humidity Statistics");
                sql1 = "UPDATE sensors SET sum=?, mean=?, sd=? WHERE sensor=?";
                PreparedStatement statement2 = connection.prepareStatement(sql1);
                statement2.setFloat(1, humidsum);
                statement2.setFloat(2, humidmean);
                statement2.setFloat(3, humidsd);
                statement2.setString(4, "humidity");
                statement2.executeUpdate();
            }
            if (sensorStates.get("light").equals("1")) {
                System.out.println("updating light Statistics");
                sql1 = "UPDATE sensors SET sum=?, mean=?, sd=? WHERE sensor=?";
                PreparedStatement statement3 = connection.prepareStatement(sql1);
                statement3.setFloat(1, lightsum);
                statement3.setFloat(2, lightmean);
                statement3.setFloat(3, lightsd);
                statement3.setString(4, "light");
                statement3.executeUpdate();
            }
          //  connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int[][] getStatistics() {
        int[][] statistics = new int[3][3];
        try {
            int countx = 0;
            Statement statement = connection.createStatement();
            String sql = "SELECT *  FROM sensors";
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                statistics[countx][0] = resultset.getInt("sum");
                statistics[countx][1] = resultset.getInt("mean");
                statistics[countx][2] = resultset.getInt("sd");
                countx++;
            }
            connection.close();
        } catch (Exception e) {

        }


        return statistics;
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateThreshold(List<Integer> thresholds) {
        try {
            String sql = "UPDATE sensors SET threshold=? WHERE sensor=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1,thresholds.get(0));
            statement.setString(2, "temperature");
            statement.executeUpdate();
            sql = "UPDATE sensors SET threshold=? WHERE sensor=?";
            statement = connection.prepareStatement(sql);
            statement.setFloat(1,thresholds.get(1));
            statement.setString(2, "humidity");
            statement.executeUpdate();
            sql = "UPDATE sensors SET threshold=? WHERE sensor=?";
            statement = connection.prepareStatement(sql);
            statement.setFloat(1,thresholds.get(2));
            statement.setString(2,"light");
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Integer> getTolerences() {
        List<Integer> tolerences = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT threshold  FROM sensors";
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                tolerences.add(resultset.getInt("threshold"));
            }
        }catch (Exception e) {
                e.printStackTrace();
            }
        return tolerences;
    }
}
