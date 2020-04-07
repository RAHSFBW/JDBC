import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface AuthorsDAO1 {
    /**
     * 将author对象添加到数据库中
     * @param connection
     * @param authors
     */
    void insert(Connection connection, Authors authors);

    /**
     * 针对指定的ID，删除表中的一条记录
     * @param connection
     * @param id
     */
    void deleteById(Connection connection, int id);

    /**
     *  针对对象authors,修改数据表中的记录
     * @param connection
     * @param authors
     */
    void updateById(Connection connection, Authors authors);

    /**
     * 针对指定的ID查询得到对应的Authors对象
     * @param connection
     * @param id
     */
    Authors getAuthorsById(Connection connection, int id);

    /**
     * 查询表中的所有记录
     * @param connection
     * @return
     */
    List<Authors> getALL(Connection connection);

    /**
     * 返回数据表中的数据的条目数
     * @param connection
     * @return
     */
    //count返回的是Long类型
    Long getCount(Connection connection) throws Exception;

    /**
     * 返回数据表中最大的生日
     * @param connection
     * @return
     */
    Date getMaxBirth(Connection connection);


}
