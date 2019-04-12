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
3. 给tomcat安装证书
  - 切换到一个目录，比如用户目录，在地址栏输入cmd，回车
  - 使用jdk的工具生成用于测试的服务器证书   
        输入命令：` keytool -genkey -alias tomcat -keyalg RSA `   
        在用户目录下会生成一个.keystore文件
  - 打开pom文件里tomcat7插件的地方配置并添加配置如下：   
```
  <httpsPort>8080</httpsPort>
  <keystoreFile>C:\Users\Mike\.keystore</keystoreFile>
  <keystorePass>123456</keystorePass>
```
4. 再次运行程序并访问 127.0.0.1/hello
---
#### FORM实现方式：
1. 写一个servlet
2. 在tomcat的conf/tomcat-users.xml中定义角色、用户、用户密码。
3. 开启认证，使用FORM认证   
  - 定义一个登陆表单   
要让容器工作，action必须是j_security_check，name必须是j_username，password必须是j_password
```
  <form method="POST" action="j_security_check">
      <input type="text" name="j_username">
      <input type="password" name="j_password">
      <input type="submit" value="Enter">
  </form>
```
  - 定义一个错误页面
```
  <html>
      <body>
          Sorry!
      </body>
  </html>
```
  - 在web.xml中使用标签<login-config>把两个表单关联起来。   
```
  <login-config>
    <auth-method>FORM</auth-method>
    <form-login-config>
      <form-login-page>/login.jsp</form-login-page>
      <form-error-page>/error.jsp</form-error-page>
    </form-login-config>
  </login-config>
```
4. 授权
  - 在web.xml中使用标签<security-role>定义角色列表
  - 在web.xml中使用标签<security-constraint>定义资源/方法约束、定义哪些角色可以访问   
5. 实现数据的机密性和完整性——基于ssl的https
  - 在<security-constraint>标签中添加以下内容即可
```
  <user-data-constraint>
    <transport-guarantee>CONFIDENTIAL</transport-guarantee>
  </user-data-constraint>
```  
NONE，没有保护，和不配置一样   
CONFIDENTIAL，在传输中数据不能被别人看到   
INTEGRAL，在传输中数据不可更改   
尽管规范没有要求，但实际中几乎所有容器都使用ssl来实现可靠传输，所以CONFIDENTIAL和INTEGRAL效果是一样的，任何一个都能提供数据的机密性和完整性。

---
#### http与https
##### ssl，安全套接字   
基于tcp上的http不安全，非法窃听者获得的请求副本里的数据是完全可读的形式   
基于tcp上的ssl上的http，非法窃听者获得的请求副本里的数据是不可读懂的形式；并不是每个请求和响应都必须安全。   
##### 容器实现原理   
用户使用http请求受限资源 --> 容器发现受限资源配置了CONFIDENTIAL，是有传输保证的，而这个请求不是https请求；响应301重定向，要求使用https -->
浏览器使用https请求受限资源 --> 容器发现资源是受限的，而用户还没有认证；响应401 --> 用户输入用户名和密码，以https方式再次请求 --> 容器认证、授权
