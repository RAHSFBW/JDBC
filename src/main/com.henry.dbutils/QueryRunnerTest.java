import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryRunnerTest {
    //插入
    @Test
    public void testInsert() {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = JDBCUtils1.getConnection3();
            String sql = "insert into book(bName,price,publishDate) Values(?,?,?)";
            int update = queryRunner.update(connection, sql, "牛五", "2000", "2019-10-27");

            System.out.println("添加了" + update + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, null);
        }


    }

    //查询
    @Test
    public void testQuery1()  {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = JDBCUtils1.getConnection3();
            String sql = "select * from authors where id = ?";
            BeanHandler<Authors> rsh = new BeanHandler<>(Authors.class);    //BeanHandler 是ResultSetHandler接口的实现
            Authors authors = queryRunner.query(connection, sql, rsh, 7);

            System.out.println(authors);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils1.closeResources(connection, null);
        }


    }

    //多条记录的查询
    @Test
    public void testQuery2()  {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = JDBCUtils1.getConnection3();
            String sql = "select * from authors where id < ?";
            BeanListHandler<Authors> rsh = new BeanListHandler<>(Authors.class);    //BeanHandler 是ResultSetHandler接口的实现
            List<Authors> list = queryRunner.query(connection, sql, rsh, 7);

            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils1.closeResources(connection, null);
        }

    }

    //特殊值的查询 : ScalarHandler
    @Test
    public void testQuery3()  {
        Connection connection = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = JDBCUtils1.getConnection3();
            String sql = "select max(birth) from authors ";
            ScalarHandler<Object> objectScalarHandler = new ScalarHandler<>();
            Object query = queryRunner.query(connection, sql, objectScalarHandler);

            System.out.println("个数为" + query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils1.closeResources(connection, null);
        }
    }

        //自定义方法,了解一下如何实现接口就行
        @Test
        public void testQuery4 ()  {
            Connection connection = null;
            try {
                QueryRunner queryRunner = new QueryRunner();
                connection = JDBCUtils1.getConnection3();
                String sql = "select * from authors where id = ? ";
                //实现ResultSetHandler接口
                ResultSetHandler<Authors> rsh = new ResultSetHandler<Authors>() {
                    @Override
                    public Authors handle(ResultSet resultSet) throws SQLException {
                        if (resultSet.next()) {              //判断结果集的下一条是佛有数据，如果有数据返回true，并指针下移

                            int id = resultSet.getInt(1);
                            String an_name = resultSet.getString(2);
                            String nation = resultSet.getNString(3);
                            Date birth = resultSet.getDate("birth");
                            Authors authors = new Authors(id, an_name, nation,birth);
                            return authors;
                        }
                        return null;
                    }
                };
                Authors authors = queryRunner.query(connection, sql, rsh, 1);
                System.out.println(authors);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                JDBCUtils1.closeResources(connection, null);
            }


        }
}


