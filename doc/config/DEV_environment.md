开发开发环境
========
###1 源码管理工具配置
源码管理使用git，托管在github上面，如果不是开发者直接到主页下载源码https://github.com/leungjianjun/Dota2Trade，
如果是开发者，需要管理员添加到成员中。然后配置git、获取源码。
方法一、安装github客户端：
- 下载安装 github for windows
- 运行，登录你的帐号，直接clone项目到本地。
- 需要获取最新代码，直接同步。修改代码后，直接点提交。一步到位。
github客户端是一个非常简单好用的版本控制系统，使用方法如下图所示
![](images/dev1.png?raw=true)

方法二、使用git命令行配置，这个流程在这里： https://help.github.com/
- 初始化Git，参考这里 https://help.github.com/articles/set-up-git
- Clone项目，参考这里 https://help.github.com/articles/fork-a-repo
- 提交 使用git push，从远程获取 使用git pull

###2 JDK环境配置
- 下载jdk7并且安装，下载地址 http://www.oracle.com/technetwork/java/javase/downloads/index.html
- 配置环境变量，右击“计算机”->选择属性->选择“高级系统设置”->环境变量->选择Path 。编辑Path属性，
在Path属性值后面加上 ```;JAVA安装目录\bin;JAVA安装目录\jre\bin```

###3 安装WAMP配置数据库
- 到这里下载http://wampserver.com/ 下载wamp server并且安装
- 运行，并且打开phpmyadmin
- 直接把doc/data文件按里面的sql文件导入数据库

###4 安装Maven
- 下载 解压maven 2 ，这里取个巧，不用配置maven环境变量，直接在下一步IDE中配置
- 提示1：开发人员间可以直接拷贝.m2文件夹，减少下载依赖文件时间
- 提示2：使用天朝Maven镜像加快下载速度 http://maven.oschina.net/help.html

###5 安装IDE
可以安装任何的支持maven的java开发IDE，这里直接使用Intellij
- 下载安装Intellij，地址 http://www.jetbrains.com/idea/
- 在setting中配置maven主目录 File->Setting->Maven->Maven Home Override
- 导入项目File->Import Project->选择项目主目录->Maven类型->默认JDK
- 运行，在菜单栏里面有个齿轮一样的图标，点一下，有一个 Edit Configuration，点击 弹出运行配置。
  添加一个Maven，命令输入 jetty:run，名字就叫jetty，点击OK完成配置。点击三角形一样的播放按钮，运行
- 打开http://127.0.0.1:8080/index.html ，就能看到主页

###6 更多开发建议
- Spring MVC开发，参考这里 http://static.springsource.org/spring/docs/3.2.0.M1/reference/html/mvc.html
- Spring JDBC开发，参考这里 http://docs.spring.io/spring/docs/3.2.0.M1/reference/html/jdbc.html
- Velocity模版引擎语法，参考这里 http://velocity.apache.org/engine/releases/velocity-1.7/user-guide.html
- Html、css、javascript规范，参考这里 https://github.com/leungjianjun/NovelProject/blob/master/docs/Design_principle/front_side_principle.md