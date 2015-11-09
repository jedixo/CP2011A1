import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by magikarp on 16/05/2015.
 */
public class WeatherGenerator extends TimerTask{
    public static Timer timer;
    private statisticDAO dao;
    private int tempValue;
    private int humidValue;
    private int lightValue;

    public void run() {
        dao = new statisticDAO();
       // System.out.println("timer is running");

        tempValue = randInt(16, 38);
        humidValue = randInt(10, 90);
        lightValue = randInt(0, 100);

        dao.updateStatistics(tempValue, humidValue, lightValue);
        dao.close();

    }


    public static int randInt(int min, int max) {
        Random rand = new Random();

        return rand.nextInt((max-min) + 1) + min;
    }

}
