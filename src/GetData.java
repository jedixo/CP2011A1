import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by magikarp on 20/05/2015.
 */
@WebServlet(name = "GetData", urlPatterns = "/getData")
public class GetData extends HttpServlet {
    private statisticDAO dao1;
    private sensorDAO doa2;
    private String statString;
    private String output;
    int[][] statistics;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        dao1 = new statisticDAO();
        doa2 = new sensorDAO();
        statString = "";
        output = "";
        statistics  = doa2.getStatistics();
        for (int i = 0; i < statistics.length; i++) {
            for (int j = 0; j < statistics[i].length; j++) {
                statString = statString + "," + statistics[i][j];
            }
        }

        int[] data = dao1.getLatestData();
        for (int i = 0; i < data.length; ++i) {
            if (i == 0) {
                output = output + data[i];
            } else {
                output = output + "," + data[i];
            }
        }
        output = output + statString;

        //System.out.println(output);
        response.setContentType("text/plain");
        response.getWriter().println(output);
        dao1.close();
        doa2.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
