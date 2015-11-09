/**
 * Created by magikarp on 23/05/2015.
 */
import junit.framework.*;

public class TestEmailConectivity extends TestCase{
    protected Emaildao dao;

    public void testAdd(){
        dao = new Emaildao();
        assertTrue(dao != null);
    }
}
