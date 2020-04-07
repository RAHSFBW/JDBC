import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * 操作访问数据库
 * 1.通过surround with 添加 try catch
 * 2.日期格式化问题
 * 3.资源注意关闭
 * 4.sql语句中的占位符
 *
 * 5.由于任何sql操作都涉及到了数据库连接和资源关闭操作，可以把它们都放在一个工具类中方便实现
 * 6.由于增删改操作只是sql语句不同，可以用一个通用方法实现，见update（）
 * 注意：如果操作的表名为关键字，用`   `括起来
 */
public class PreparedStatementUpdateTest {
    //向books.book增加一条记录
    @Test
    public void testInsert()  {//添加到资源关闭，去掉throws
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.读取配置文件的4个配置信息,通过类加载器,一个系统加载器( ConnectionTest.class.getClassLoader().getResourceAsStream)
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
            connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);

            //4.通过连接预编译SQL语句，返回 PreparedStatement 的实例
            String sql = "insert into book(bName,price,publishDate)values(?,?,?)";//? 占位符     '张三',55,20100101
            preparedStatement = connection.prepareStatement(sql);

            //5.填充占位符
            preparedStatement.setString(1,"李四");
            preparedStatement.setDouble(2,100);

            //格式化
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parse = simpleDateFormat.parse("1999-09-09");
            preparedStatement.setDate(3,new Date(parse.getTime()));

            //6.执行操作
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //7.资源关闭
            try {
                if(preparedStatement!=null)
                    preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    //通过调用工具包中的类，修改一条记录
    @Test
    public void bookUpdate() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据可连接
            connection = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            String sql = "update book set price = ? where bNAME = ?";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setObject(1, 2000);
            preparedStatement.setObject(2,"李四");
            //4.执行操作
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            JDBCUtils.closeResources(connection,preparedStatement);
        }

    }

    //设计一个通过的增删改操作
    public void update(String sql, Object ... args)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据可连接
            connection = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0;i<args.length;++i) {//可变形参看做数组
                preparedStatement.setObject(i+1, args[i]);//!!!!注意
            }
            //4.执行操作
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            JDBCUtils.closeResources(connection,preparedStatement);
        }

    }
    //对update测试
    @Test
    public void testCommonUpdate(){
        String sql ="delete from book where id = ?" ;
        update(sql,3);//删除牛五
    }
}
