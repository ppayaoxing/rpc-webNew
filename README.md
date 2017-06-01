1.通过 mvn mybatis-generator:generate 可以按数据库表生成相应的bean和xml文件


2.通过 mvn com.xw:mvPlugin:1.0:mv 可以把生成的相应的bean和接口移动到rpc-api里面 以便更好的进行代码整合，通用


3.项目依赖 mysqlPlugin 用来生成mybatis分页方法和mvPlugin 移动文件的maven插件
 
4.自定义spring 标签 并和hessian整合 实现rpc通信框架 并具有负载均衡和异常重试机制

5.spring和quartz整合实现自定义任务，结合数据库的乐观锁，字符串同步机制可以实现分布式任务