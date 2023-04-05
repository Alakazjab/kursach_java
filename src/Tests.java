import Model.DbCon;
import org.checkerframework.checker.fenum.qual.SwingTextOrientation;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class Tests {
    @Test
    public void testConnection(){
        assertNotNull(new DbCon().connect());
    }
    @Test
    public void testUserStatus(){
        try {
            String email = new DbCon().auth_user("alakazjab@yandex.ru");
            assertEquals(email,"user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
