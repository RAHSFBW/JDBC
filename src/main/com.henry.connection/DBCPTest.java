import com.mchange.v2.c3p0.DataSources;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.util.Properties;

public class DBCPTest {

    @Test
    //方式二
    public void testGetConnection() throws Exception {
        Properties properties = new Properties();
//        //1.
//        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("src/dbcp.properties");
        //2.
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\RAHS\\IdeaProjects\\JDBC\\src\\main\\resources\\dbcp.properties"));


        properties.load(inputStream);
        //创建一个数据连接池
        DataSource dataSources = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = dataSources.getConnection();
        //3.
//        Properties properties=new Properties();
//        properties.load(new FileReader("C:\\Users\\RAHS\\IdeaProjects\\JDBC\\src\\main\\resources\\dbcp.properties"));//从文件中读取设置值
//        DataSource dataSource=BasicDataSourceFactory.createDataSource(properties);//设置数据库连接池
//        Connection connection=dataSource.getConnection();
       System.out.println(connection);

    }
}
