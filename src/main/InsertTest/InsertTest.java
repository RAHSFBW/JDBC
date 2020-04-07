import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertTest {
    @Test
    public void testInsertFinal() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long start = System.currentTimeMillis();

            connection = JDBCUtils.getConnection();
            //设置不自动提交
            connection.setAutoCommit(false);

            String sql = "Insert into goods(name) values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<1000000;i++){
                preparedStatement.setObject(1,"name_"+i);

                preparedStatement.addBatch();
                if(i%500 ==0){
                    preparedStatement.execute();

                    preparedStatement.clearBatch();
                }
            }

            //提交数据
            connection.commit();

            long end =System.currentTimeMillis();
            System.out.println("花费时间为： "+(end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection,preparedStatement);
        }

    }
}
