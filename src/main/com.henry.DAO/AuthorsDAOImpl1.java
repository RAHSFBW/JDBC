import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class AuthorsDAOImpl1 extends BaseDAO1<Authors> implements AuthorsDAO1 {
    /**
     *
     * 在基础上，传入父类的泛型（Authors），删去了Authors的对象传入
     * @param connection
     * @param authors
     */

    @Override
    public void insert(Connection connection, Authors authors) {
        String sql = "insert into authors(an_name,nation,birth)values(?,?,?)";
        update(connection,sql,authors.getAn_name(),authors.getNation(),authors.getBirth());

    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql = "delete from authors where id = ?";
        update(connection,sql,id);
    }

    @Override
    public void updateById(Connection connection, Authors authors) {
        String sql = "update authors set an_name = ?,nation = ?,birth = ? where id = ?";
        update(connection, sql,authors.getAn_name(),authors.getNation(),authors.getBirth(),authors.getId());
    }

    @Override
    public Authors getAuthorsById(Connection connection, int id) {
        String sql = "select id,an_name,nation,birth from authors where id = ?";
        Authors author = getInstance(connection, sql, id);
        return author;
    }

    @Override
    public List<Authors> getALL(Connection connection) {
        String sql = "select id,an_name,nation,birth from authors";
        List<Authors> forList = getForList(connection, sql);
        return forList;
    }

    @Override
    public Long getCount(Connection connection)  {
        String sql = "select count(*) from authors";
        return getValue(connection, sql);

    }

    @Override
    public Date getMaxBirth(Connection connection) {
        String sql = "select max(birth) from authors";
        return getValue(connection,sql);

    }
}
