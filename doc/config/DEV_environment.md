开发开发环境
========
###源码管理工具配置
源码管理使用git，托管在github上面，如果不是开发者直接到主页下载源码https://github.com/leungjianjun/Dota2Trade，
如果是开发者，需要管理员添加到成员中。然后配置git、获取源码。这里取个巧，按照最简单步骤：
- 下载安装 github for windows
- 运行，登录你的帐号，直接clone项目到本地。
- 需要获取最新代码，直接同步。修改代码后，直接点提交。一步到位。

###JDK环境配置
- 到这里http://www.oracle.com/technetwork/java/javase/downloads/index.html下载jdk7 并且安装
- 配置环境变量，看这里http://win7452.blog.51cto.com/147513/122792

###安装WAMP配置数据库
- 到这里下载http://wampserver.com/ 下载wamp server并且安装
- 运行，并且打开phpmyadmin
- 直接把doc/data文件按里面的sql文件导入数据库

###安装Maven
- 下载 解压maven 2 ，这里取个巧，不用配置maven环境变量，直接在下一步IDE中配置
- 提示：开发人员间可以直接拷贝.m2文件夹，减少下载依赖文件时间

###安装IDE
可以安装任何的支持maven的java开发IDE，这里直接使用Intellij
- 下载安装Intellij，地址 http://www.jetbrains.com/idea/
- 在setting中配置maven主目录
- 导入项目
- 运行，在菜单栏里面有个齿轮一样的图标，点一下，有一个 Edit Configuration，点击弹出运行配置。
  添加一个Maven，命令输入 jetty:run，名字就叫jetty，点击OK完成配置。点击三角形一样的播放按钮，运行
- 打开http://127.0.0.1:8080/index.html ，就能看到主页