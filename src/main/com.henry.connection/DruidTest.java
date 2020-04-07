import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.io.*;
import java.util.Properties;


public class DruidTest {
    @Test
    public void getConnection() throws Exception {
        Properties properties = new Properties();

        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        properties.load(inputStream);
        //获取连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();

        System.out.println(connection);

    }


}
