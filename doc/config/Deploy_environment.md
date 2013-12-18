部署配置
=========
简单3部就能运行程序：

###1 JDK环境配置
- 到这里http://www.oracle.com/technetwork/java/javase/downloads/index.html下载jdk7 并且安装
- 配置环境变量，看这里http://win7452.blog.51cto.com/147513/122792

###2 安装WAMP配置数据库
- 到这里下载wamp http://wampserver.com/ 并且安装
- 运行，并且打开phpmyadmin
- 直接把doc/data文件按里面的sql文件导入数据库

###3 运行jar
在命令行中输入 java -jar [war文件],弹出服务器的控制界面，点击start即可
![](images/deploy1.png?raw=true)