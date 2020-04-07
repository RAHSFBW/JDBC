import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class AuthorsDAOImplTest {

    private AuthorsDAOImpl dao = new AuthorsDAOImpl();
    @Test
    public void insert()  {
        Connection connection = null;
        try {
            connection = JDBCUtils1.getConnection1();
            Authors authors = new Authors(1, "大仲马", "法国", new Date(19888889L));
            dao.insert(connection,authors);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,null);
        }
    }

    @Test
    public void deleteById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            dao.deleteById(connection, 5);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,null);
        }
    }

    @Test
    public void updateById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Authors authors = new Authors(4,"于谦","大中国", new Date(1888888L));
            dao.updateById(connection,authors);
            System.out.println("全部修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,null);
        }
    }

    @Test
    public void getAuthorsById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Authors authorsById = dao.getAuthorsById(connection, 4);
            System.out.println("结果是 ："+authorsById);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,null);
        }
    }

    @Test
    public void getALL() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            List<Authors> all = dao.getALL(connection);
            all.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,null);
        }
    }

    @Test
    public void getCount() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Long count = dao.getCount(connection);
            System.out.println("个数为: "+count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,null);
        }
    }

    @Test
    public void getMaxBirth() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(connection);
            System.out.println("the maxBirth is : "+maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,null);
        }
    }
}