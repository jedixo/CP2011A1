import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by magikarp on 14/05/2015.
 */
@WebServlet(name = "getsensors", urlPatterns = "/getSensors")
public class getsensors extends HttpServlet {
    private sensorDAO dao;
    private  List<String> sensors;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        dao = new sensorDAO();

        sensors = dao.getSensorNameandState();
        response.setContentType("text/plain");

        for (String sensor : sensors) {
            response.getWriter().println(sensor);
        }
        dao.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
