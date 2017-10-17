#Rippleinfo Data Analytic Module -- Rippleinfo 数据分析模块

##运行之前的准备
###数据库
Spring batch需要数据库记录job运行的状态，因此该程序需要连接数据库。运行之前，请运行如下命令建立数据库。
```mysql
CREATE DATABASE batch_db DEFAULT CHARACTER SET utf8 ;
```
