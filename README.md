# ulyer-generator

在线配置代码生成器
<hr/>  

### 项目环境 

| 环境 | 版本 | 
| :-----| ----: | 
| maven | 3.6 | 
| jdk | 1.8 |
| springboot | 2.6.1 |
| mongodb | 5.0.5 |
| freemaker | boot-starter 2.6.1 |
| vue | 3.2.26 |
| ant-design | 2.2.8 |
| codemirror | 5.64.0 |


### 前端变量说明(static/js/\* ,/templates/\*,static/lib/\*)
|  变量  | 说明  |
|  ----  | ----  |
| antd  | ant design 全局变量 使用message 方法等|
| Vue  | Vue3全局变量 |
|axios|axios http请求|
|context|web容器上下文 js中使用|
|contextPath|freemaker容器上下文|
|CodeMirror|codemirror.js 代码编辑器 全局对象|

### 如何使用

* git clone 项目
* 准备上述环境
* 创建mongodb collection 或者自动创建
* 运行UlyerGeneratorApplication
* 访问/page/tables路径 进行项目配置

<hr/>   

### 功能说明 
#### 数据源配置
配置导入表需要的数据源信息
#### 生成表管理
管理反生成java文件的表配置 列等信息 javaType等
#### 模板管理(模块)
* 生成文件的模板  
* 模板可以自定义文件名生成规则 使用springEL表达式解析名称包含/时会在上级生成目录   
* 模块包含多个模板 生成时勾选模板进行生成 方便分组

#### 


#### 常见问题
1.如何扩展使用其他模板插件 如 thymeleaf
* 实现TemplateGenerator接口
* 继承BaseTemplateGenerator  

2.只能用于java项目生成吗?
* 并不是 生成的文件可以自己定义类型，文件名规则自定义
* 使用扩展变量自定义生成

3.必须根据table才能生成文件?
* 根据模板生成文件不一定必须要勾选表
* 勾选表生成文件规则为 tables.forEach(gen) 不选择使用扩展变量生成即可    


4.如何新增数据源类型  sqlServer PostgreSQL Oracle?
* 新增DataSourceHelper实现类实现接口方法
* 新增DbProperty实现类设置链接属性
* DataBaseTypes 新增枚举


#### 部分截图

