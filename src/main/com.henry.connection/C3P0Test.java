
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Test {

    //方式一
    @Test
    public void testGetConnection() throws PropertyVetoException, SQLException {
        //获取C3P0数据库连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/books");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("03241X");
        //设置相关的参数，对数据库连接池进行管理
        //设置初始数据库连接池中的连接数
        comboPooledDataSource.setInitialPoolSize(10);
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println(connection);
        //销毁连接池
        //DataSources.destroy(comboPooledDataSource);
    }

    //方式二,使用配置文件（c3p0-config.xml）
    @Test
    public void testGetConnection1() throws SQLException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("mySource");
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println(connection);
    }
}
