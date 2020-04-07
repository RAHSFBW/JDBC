import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    //方式一
    @Test
    public void testConnection1() throws SQLException{
        //获取Driver类实现对象
        Driver driver = new com.mysql.jdbc.Driver();//java.sql.Driver接口是所有JDBC驱动程序需要实现的接口，这个接口是提供给不同厂商使用的
        //jdbc:mysql  协议
        //localhost ip地址
        //3306  默认mysql端口号
        //books  数据库datebase
        //useSSL=false  MySql在高版本需要指明是否进行SSL连接,这里是false
        String url = "jdbc:mysql://localhost:3306/books?useSSL=false";

        //将用户名和密码封装在Properties
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","03241X");

        Connection connection = driver.connect(url,info);       //返回一个连接
        System.out.println(connection);
    }

    //方式二，对方式一的迭代
    @Test
    public void testConnection2() throws Exception {//ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
        //获取Driver实现对象，使用反射
        //使用Class类的中静态forName()方法获得与字符串对应的Class对象
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)clazz.getDeclaredConstructor().newInstance();//clazz.newInstance会直接调用该类的无参构造函数进行实例化,以被弃用
        //getDeclaredConstructor()方法会根据他的参数对该类的构造函数进行搜索并返回对应的构造函数，没有参数就返回该类的无参构造函数，然后再通过newInstance进行实例化。

        //提供需要连接的数据库。基本信息
        String url = "jdbc:mysql://localhost:3306/books?useSSL=false";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","03241X");

        //获取连接
        Connection connection = driver.connect(url,info);       //返回一个连接
        System.out.println(connection);

    }

    //方式三，使用DriverManager替换Driver,反射
    @Test
    public void testConnection3() throws  Exception{
        //基本信息
        String url = "jdbc:mysql://localhost:3306/books?useSSL=false";
        String user = "root";
        String password = "03241X";

        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)clazz.getDeclaredConstructor().newInstance();

        //注册驱动
        DriverManager.registerDriver(driver);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }

    //方式四，对三进行优化:
    @Test
    public void testConnection4() throws  Exception{
        //1、基本信息
        String url = "jdbc:mysql://localhost:3306/books?useSSL=false";
        String user = "root";
        String password = "03241X";


        Class.forName("com.mysql.jdbc.Driver");  //为了向下兼容不建议省略  JDBC4.0自动加载驱动器类： 从JDK1.6开始，Oracle就将修改了添加了新的加载JDBC驱动的方式。即JDBC4.0。在启动项目或是服务时，会判断当前classspath中的所的jar包，并检查META-INF目录下，是否包含services文件夹，如果包含，就会将里面的配置加载成相应的服务。

        //Driver实现类中，具有静态块自动注册驱动DriverManager.registerDriver(new Driver());
        // Driver driver = (Driver)clazz.getDeclaredConstructor().newInstance();
        //2、注册驱动
        // DriverManager.registerDriver(driver);

        //3、获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }

    //方式五，the final ，将基本信息写入配置文件，注意路径问题！！！！
    @Test
    public void testConnection5() throws IOException, ClassNotFoundException, SQLException {

        //1.读取配置文件的4个配置信息,通过类加载器,一个系统加载器
        InputStream inputStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
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
        System.out.println(connection);
    }
}


