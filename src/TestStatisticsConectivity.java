import junit.framework.TestCase;

/**
 * Created by magikarp on 23/05/2015.
 */
public class TestStatisticsConectivity  extends TestCase{
    protected statisticDAO dao;

    public void testAdd(){
        dao = new statisticDAO();
        assertTrue(dao != null);
    }
}
