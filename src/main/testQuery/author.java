import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 对author表的查询操作
 */
public class author {

    @Test
    public void testForQurey()  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,an_name,nation from author where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,2);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){              //判断结果集的下一条是佛有数据，如果有数据返回true，并指针下移

                int id =resultSet.getInt(1);
                String an_name = resultSet.getString(2);
                String nation = resultSet.getNString(3);

                //方式一
               // System.out.println(id+an_name+nation);

                //方式二
               // Object[] objects = {id, an_name, nation};

                //方式三
                Authors authors = new Authors(id, an_name, nation);
                System.out.println(authors);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //关闭资源
            JDBCUtils.closeResources(connection,preparedStatement,resultSet); }

    }

    /**
     * 通用的查询操作
     * @param sql
     * @return
     */
    public Authors queryForAuthors(String sql, Object...args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            for(int i =0; i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据(用来存放结果集的修饰，如列名等等)
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取结果集的列数
            int columnCount = metaData.getColumnCount();
            if(resultSet.next()){
                Authors authors = new Authors();

                for(int i =0;i<columnCount;i++){
                    //获取列值（id）
                    Object columnValue = resultSet.getObject(i + 1);

                    //获取列名，但在列名与类中属性名不一样时会报错,所以替换label
                   // String columnName = metaData.getColumnName(i + 1);
                    //1.当列名与类属性名不一致时，sql语句要取别名。2.当一致的时候，效果与getColumnName一致
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //给authors对象指定的columnName属性，赋值为columnValue，通过反射
                    Field declaredField = Authors.class.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(authors, columnValue);
                }
                return authors;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,preparedStatement,resultSet);

        }
        return null;
    }


    @Test
    public void testQueryForAuthors(){
      //  String sql = "select id,an_name,nation from author where id = ?";
        String sql1 = "select an_name ,nation from author where id = ?";
     //   Authors authors = queryForAuthors(sql, 1);
     //   System.out.println(authors);
        //测试别名

        Authors authors1 = queryForAuthors(sql1, 2);
        System.out.println(authors1);
    }

}
