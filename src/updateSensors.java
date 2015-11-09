
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by magikarp on 14/05/2015.
 */
@WebServlet(name = "updateSensors" , urlPatterns = "/updateSensorConfig")
public class updateSensors extends HttpServlet {

    private sensorDAO dao;
    private String[] sensors;
    private int tempstate;
    private int humidstate;
    private int lightstate;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {
            tempstate = 0;
            humidstate = 0;
            lightstate = 0;

            dao = new sensorDAO();
            sensors = request.getParameterValues("sensor");
            try {
                for (String sensor : sensors) {
                   // System.out.println(sensor);
                    if (sensor.equals("temperature")) {
                        tempstate = 1;
                    }
                    if (sensor.equals("humidity")) {
                        humidstate = 1;
                    }
                    if (sensor.equals("light")) {
                        lightstate = 1;
                    }

                }
            } catch (Exception e) {

            }

            dao.updateState(tempstate, humidstate, lightstate);

            dao.close();
            response.sendRedirect("/setup.html");

        } catch (Exception e){
            System.out.println(e);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
