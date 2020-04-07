import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO:date(base) access object
 */
public abstract class BaseDAO1<T> {

    private Class<T> clazz = null;

    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();//获取了父类的泛型参数
        clazz = (Class<T>) actualTypeArguments[0];
    }

    /**
     * 通用的增删改操作 2.0————transaction.java
     */
    public int update(Connection connection, String sql, Object ... args) {

        PreparedStatement preparedStatement = null;
        try {
//            //1.获取数据可连接
//            connection = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0;i<args.length;++i) {//可变形参看做数组
                preparedStatement.setObject(i+1, args[i]);//!!!!注意
            }
            //4.执行操作
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //修改为自动提交，针对于使用数据库连接池的情况（使用的连接会被归还到连接池，所以要恢复）
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            //5.关闭资源
            JDBCUtils.closeResources(null,preparedStatement);
        }
        return 0;
    }


    /**
     * 通用的查询操作，返回一条记录的操作-----PreparedStatementQueryTest
     *
     */
    public T getInstance(Connection connection, String sql, Object...args){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {


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
            JDBCUtils.closeResources(null,preparedStatement,resultSet);

        }
        return null;
    }


    /**
     * 对于一组结果的查询操作
     *
     */
    public  List<T> getForList(Connection connection, String sql, Object...args){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {


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
            JDBCUtils.closeResources(null,preparedStatement,resultSet);

        }
        return null;
    }

    /**
     * 用于查询特殊值，如count（*）
     * @param connection
     * @param sql
     * @param args
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E> E getValue(Connection connection, String sql, Object...args)  {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i =0;i<args.length;i++){
                preparedStatement.setObject(i+1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null,preparedStatement,resultSet );
        }
        return null;
    }
}
