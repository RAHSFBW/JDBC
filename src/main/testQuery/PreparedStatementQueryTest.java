import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;


public class PreparedStatementQueryTest {
    /**
     * 只针对查询返回一条记录的操作
     *
     */
    public <T>T getInstance(Class<T> clazz, String sql, Object...args){
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
                T t = clazz.getDeclaredConstructor().newInstance();

                for(int i =0;i<columnCount;i++){
                    //获取列值（id）
                    Object columnValue = resultSet.getObject(i + 1);

                    //获取列名，但在列名与类中属性名不一样时会报错,所以替换label
                    // String columnName = metaData.getColumnName(i + 1);
                    //1.当列名与类属性名不一致时，sql语句要取别名。2.当一致的时候，效果与getColumnName一致
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //给authors对象指定的columnName属性，赋值为columnValue，通过反射
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,preparedStatement,resultSet);

        }
        return null;
    }

    @Test
    public void testGetInstance(){
        String sql = "select an_name ,nation from author where id = ?";
        Authors authors = getInstance(Authors.class, sql, 4);
        System.out.println(authors);
    }


    /**
     * 对于一组结果的查询操作
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> getForList(Class<T> clazz, String sql, Object...args){
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

            // 创建集合对象
            ArrayList<T> list = new ArrayList<>();
            while (resultSet.next()){
                T t = clazz.getDeclaredConstructor().newInstance();

                for(int i =0;i<columnCount;i++){
                    //获取列值（id）
                    Object columnValue = resultSet.getObject(i + 1);

                    //获取列名，但在列名与类中属性名不一样时会报错,所以替换label
                    // String columnName = metaData.getColumnName(i + 1);
                    //1.当列名与类属性名不一致时，sql语句要取别名。2.当一致的时候，效果与getColumnName一致
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //给authors对象指定的columnName属性，赋值为columnValue，通过反射
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,preparedStatement,resultSet);

        }
        return null;
    }

    @Test
    public void testGetForList(){
        String sql = "select an_name ,nation from author where id < ?";
        List<Authors> List = getForList(Authors.class, sql, 5);
        List.forEach(System.out::println);
    }


}
