import junit.framework.TestCase;

/**
 * Created by magikarp on 23/05/2015.
 */
public class TestSensorConectivity extends TestCase{
    protected sensorDAO dao;

    public void testAdd(){
        dao = new sensorDAO();
        assertTrue(dao != null);
    }
}
