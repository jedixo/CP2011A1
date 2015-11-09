import junit.framework.TestCase;

/**
 * Created by magikarp on 23/05/2015.
 */
public class TestNotificationConectivity extends TestCase {
    protected NotificationDao dao;

    public void testAdd(){
        dao = new NotificationDao();
        assertTrue(dao != null);
    }
}
