import java.sql.Date;

/**
 * ORM编程思想（Object relational mapping)
 * 一个数据表对应一个java类
 * 表中的一条记录对应java类的一个对象
 * 表中的一个字段对应java类的一个属性
 */
public class Authors {
    private int id;
    private String an_name;
    private String nation;
    private Date birth;

    public Authors(int id, String an_name, String nation, Date birth) {
        this.id = id;
        this.an_name = an_name;
        this.nation = nation;
        this.birth = birth;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setAn_name(String an_name) {
        this.an_name = an_name;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getBirth() {
        return birth;
    }

    @Override
    public String toString() {
        return "Authors{" +
                "id=" + id +
                ", an_name='" + an_name + '\'' +
                ", nation='" + nation + '\'' +
                ", birth=" + birth +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getAn_name() {
        return an_name;
    }

    public String getNation() {
        return nation;
    }


    public Authors() {
        super();

    }

    public Authors(int id, String an_name, String nation) {
        this.id = id;
        this.an_name = an_name;
        this.nation = nation;
    }
}
