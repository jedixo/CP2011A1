import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by magikarp on 22/05/2015.
 */
@WebServlet(name = "Notification", urlPatterns = "/getNotifications")
public class Notification extends HttpServlet {

    private NotificationDao dao;
    private String submitButton;
    private List<String> notifications;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new NotificationDao();

            if (request.getParameter("button") != null) {
                submitButton = request.getParameter("button");
            } else {
                submitButton = "";
            }
            System.out.println(submitButton);

            if (submitButton.equals("delete")) {
                System.out.println("deleting");
                int id = Integer.parseInt(request.getParameter("id"));
                System.out.println(id);
                //delete database
                dao.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("/tol.html");
            } else if (submitButton.equals("")) {
                System.out.println("getting");
                //get database
                notifications = dao.getNotifications();
                response.setContentType("text/plain");

                for (String notification : notifications) {
                    response.getWriter().println(notification);
                }

            }
            dao.close();
        } catch (Exception e) {
            String submitButton = "none";
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
