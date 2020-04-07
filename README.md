# JDBC，请按以下顺序学习

## Util
这是一个工具包，包含了**JDBCUtils 和 JDBCUtils1**两个类；
1. **JDBCUtils**用来获取数据连接（获取基本信息：包括数据库的用户名、密码、Url，驱动类对象；加载驱动；返回连接实例）和关闭资源（包括连接，PreparedStatement，以及可能有的结果集resultSet）
2. **JDBCUtils1**是用来获取数据库连接池的连接的（包括C3P0,DBCP,Druid）以及关闭资源的操作（closeResources1是通过**Dbutils**，一个Apache组织提供的一个对JDBC进行简单封装的开源工具类库，来进行关闭操作的）

---

## testUpdate
1. ConnectionTest : 包含五种获取连接的方式。
2. PreparedStatementUpdateTest： 简单的增删改操作。

---

## testQuery
1.Authors：第一了一个类，即对应author数据库存储的对象。
2.author :对author库进行查询操作。

---

## InsertTest
1. 批量插入：关闭了自动提交，插入1 million条数据，500条一次提交。

---

## testDAO 和 com.henry.DAO
testDao: AuthorsDAO、AuthorsDAOImpl、BaseDAO
com.henry.DAO: 同；
**区别：**
删去了Authors对象的传入，传入了父类的泛型参数即BaseDAO1<Authors>的Authors

---

## com.henry.connection
测试获取三种数据连接池的连接

## com.henry.dbutils
使用了queryRunner(package org.apache.commons.dbutils).
