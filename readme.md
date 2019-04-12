### 原生servlet安全——数据库存储领域信息方式
---
#### 测试方法
1. 运行servlet_security.sql
2. 打包项目并改名为ROOT.war
3. 必须: 把mysql-connector-java-5.1.47-bin.jar复制到tomcat的lib目录下
4. 打开tomcat的server.xml，并添加以下内容
```
<Realm className="org.apache.catalina.realm.JDBCRealm"
   driverName="com.mysql.jdbc.Driver"
   factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
   connectionURL="jdbc:mysql://localhost:3306/servlet_security?user=root&amp;password=root"
   userTable="user"
   userNameCol="name"
   userCredCol="password"
   userRoleTable="user"
   roleNameCol="role">
</Realm>
```
5. 把ROOT.war复制到tomcat的webapps目录下，启动tomcat
6. 访问 http://127.0.0.1:8080/hello
