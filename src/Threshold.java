import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by magikarp on 22/05/2015.
 */
@WebServlet(name = "Threshold", urlPatterns = "/Threshold")
public class Threshold extends HttpServlet {

    private sensorDAO dao;
    private String submitButton;
    private List<Integer> tolerences;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new sensorDAO();

            if (request.getParameter("button") != null) {
                submitButton = request.getParameter("button");
            } else {
                submitButton = "";
            }
            tolerences = new ArrayList<>();

            if (submitButton.equals("Update")) {
                System.out.println("updating");
                tolerences.add(Integer.valueOf(request.getParameter("temperature")));
                tolerences.add(Integer.valueOf(request.getParameter("humidity")));
                tolerences.add(Integer.valueOf(request.getParameter("light")));
                //add to database
                dao.updateThreshold(tolerences);
                response.sendRedirect("/tol.html");
            } else if (submitButton.equals("")){
                System.out.println("getting");
                //get database
                tolerences = dao.getTolerences();

                response.setContentType("text/plain");
                response.getWriter().println("temperature," + tolerences.get(0));
                response.getWriter().println("humidity," + tolerences.get(1));
                response.getWriter().println("light," + tolerences.get(2));


            }
            dao.close();


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
