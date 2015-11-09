import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;

/**
 * Created by magikarp on 17/05/2015.
 */
@WebServlet(name = "startStopCollection", urlPatterns = "/StartStopData")
public class startStopCollection extends HttpServlet {



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            WeatherGenerator.timer.cancel();
            response.sendRedirect("/setup.html");
        } catch (Exception e) {
            System.out.println(e);
            long interval = Long.parseLong(request.getParameter("interval"));

            WeatherGenerator weather = new WeatherGenerator();
            Timer timer = new Timer();
            timer.schedule(weather, 0, interval);
            // makes sure the timer's reference isn't lost so it can be stopped
            weather.timer = timer;
            response.sendRedirect("/view.html");
        }

       // timer.cancel();




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
