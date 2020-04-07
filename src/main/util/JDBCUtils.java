import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 一个工具包，实现数据库连接和资源关闭
 */
public class JDBCUtils {
    public static Connection getConnection() throws Exception {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();

        properties.load(inputStream);


        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2.加载驱动
        Class.forName(driverClass);

        //3.获取连接
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

    public static void closeResources(Connection connection, Statement preparedStatement){
        try {
            if(preparedStatement !=null)
                preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if( connection != null)
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeResources(Connection connection, Statement preparedStatement, ResultSet resultSet){
        try {
            if(preparedStatement!=null)
                preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(connection!=null)
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(resultSet != null)
           resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
