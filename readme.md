### 原生servlet安全
1. FORM方式
2. 使用注解
---
#### 注解实现
1. 注释掉web.xml里的&lt;security-constraint&gt;
2. 在Servlet上添加相应的注解
---
#### 测试
1. 使用idea运行程序，关闭运行的程序
2. 打开target/tomcat/conf/tomcat-users.xml并添加如下内容
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

