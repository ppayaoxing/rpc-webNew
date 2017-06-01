1.通过 mvn mybatis-generator:generate 可以按数据库表生成相应的bean和xml文件

2.通过 mvn com.xw:mvPlugin:1.0:mv 可以把生成的相应的bean和接口移动到rpc-api里面 以便更好的进行代码整合，通用
3.项目依赖 mysqlPlugin 用来生成mybatis分页方法和mvPlugin 移动文件的maven插件