import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by magikarp on 21/05/2015.
 */
@WebServlet(name = "Email", urlPatterns = "/getEmails")
public class Email extends HttpServlet {
    private Emaildao dao;
    private String submitButton;
    List<String> emails;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            dao = new Emaildao();

            if (request.getParameter("button") != null) {
                submitButton = request.getParameter("button");
            } else {
                submitButton = "";
            }
            System.out.println(submitButton);

            if (submitButton.equals("add")) {
                System.out.println("adding");
                //add to database
                dao.addEmail(request.getParameter("address"));
                response.sendRedirect("/tol.html");
            } else if (submitButton.equals("delete")) {
                System.out.println("deleting");
                int id = Integer.parseInt(request.getParameter("id"));
                System.out.println(id);
                //delete database
                dao.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("/tol.html");
            } else if (submitButton.equals("")){
                System.out.println("getting");
                //get database
                emails = dao.getEmail();
                response.setContentType("text/plain");

                for (String email : emails) {
                    response.getWriter().println(email);
                }

            }
            dao.close();
        } catch (Exception e) {
            String submitButton = "none";
        }


        //response.getWriter().println("jake.dixon@my.jcu.edu.au,1");
       // response.getWriter().println(output);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
