import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils1 {
    /**
     * 使用C3P0连接
     * @return
     * @throws SQLException
     */
    //防止重复造池子，所以拿出来
    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("mySource");
    public static Connection getConnection1() throws SQLException {

        Connection connection = comboPooledDataSource.getConnection();

        return connection;
    }

    /**
     * DBCP
     */
    private static DataSource dataSource;
    static {
        try {
            Properties properties = new Properties();
            FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\RAHS\\IdeaProjects\\JDBC\\src\\main\\resources\\dbcp.properties"));


            properties.load(inputStream);
            //创建一个数据连接池
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection2() throws SQLException {
        Connection connection = dataSource.getConnection();

        return connection;
    }

    /**
     * Druid
     */
    private static DataSource  dataSource1;
    static {
        try {
            Properties properties = new Properties();

            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            properties.load(inputStream);
            //获取连接池对象
            DataSource dataSource1 = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection3() throws Exception {

        Connection connection = dataSource.getConnection();

       return connection;

    }


    /**
     * 关闭资源
     * @param connection
     * @param preparedStatement
     */
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
    public static void closeResources(Connection connection, Statement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public static void closeResources1(Connection connection, Statement preparedStatement, ResultSet resultSet){
            //1.
//            try {
//                DbUtils.close(connection);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            //2.
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(resultSet);

        }

}
