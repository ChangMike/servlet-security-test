### 原生servlet安全——BASIC方式
---
#### 测试方法
1. 使用idea运行程序，关闭运行的程序
2. 打开target/tomcat/cont/tomcat-users.xml并添加如下内容
  ```
  <role rolename="guest"/>
  <role rolename="member"/>
  <role rolename="admin"/>
  <user username="zs" password="123" roles="guest"/>
  <user username="ls" password="456" roles="member"/>
  <user username="ww" password="" roles="admin"/>
  ```
3. 再次运行程序，访问 127.0.0.1/hello
---
#### Servlet安全：认证、授权、数据的机密性和完整性
- 数据的机密性：传输中别人无法看到数据
- 数据的完整性：传输中别人无法篡改数据
---
#### HTTP如何认证
1. 浏览器发出请求访问资源
2. 容器查找容器的安全表中的URL，如果找到URL，再确定被访问资源是否是一个受限资源，是则响应一个未授权状态码401
3. 用户输入用户名密码，再次请求
4. 容器检查用户名密码(认证)，容器接着检查该用户的角色是否可以访问受限资源(授权)，响应；如果未通过认证，再次响应401
---
#### BASIC实现方式：
1. 写一个servlet
2. 领域——realm，存储认证信息的地方。  
tomcat：conf/tomcat-users.xml，在其中定义角色、用户、用户密码。
   - 该文件应用于web-apps下部署的所有应用。
   - 通常称之为内存领域，因为tomcat会在启动时将此文件读入到内存。
3. 认证  
   在web.xml里开启认证，有四种类型BASIC、DIGEST、CLIENT-CERT、FORM。
   - BASIC使用未加密的base64编码传输登陆信息，由于base64编码机制广为人知，所以安全性很弱。
   - DIGEST更安全，但由于其加密机制没有得到广泛使用，所以并不要求容器一定要支持。
   - CLIENT-CERT，公共密钥证书。
   - 前面三种认证都使用浏览器的标准弹出表单输入用户名和口令。FORM，定制登陆表单；但最不安全，登陆信息未经加密。
4. 授权
   servlet授权的形式：由容器确定一个特定的servlet能否被某个用户调用。
   - 定义角色  
   在web.xml中使用标签<security-role>定义角色列表
   - 在web.xml中使用标签<security-constraint>定义资源/方法约束、定义哪些角色可以访问
