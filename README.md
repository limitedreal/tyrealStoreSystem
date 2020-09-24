# spring frame work

##模式注解
@Component 使组件/类/bean加入容器 new

功能上与Component无区别，更多的是标注作用
@Service 
@Controller
@Repository


@Configuration

- @Configuration用于取代xml
原来，以配置文件的变化取代代码的变化，使得变化远离代码
现在，以@Configuration取代xml，避免复杂的xml配置，
是Configuration类在变化，实际上仍然是遵守着OCP原则

###为什么隔离到配置文件
1. 集中，易于修改
2. 清晰，只有数据没有逻辑
所以可见Configuration类同样有这样的优势


桥接点

@Autowired 
默认byType
1. 如果有0个，报错
2. 有一个，注入
3. 有多个，按照变量名注入，不能匹配时报错

## @Autowired 
使用@Qualifier手动指定注入

## 对于变化的解决方案
1. 用interface抽象起来 
- 策略模式
2.  只有一个类，更改属性，控制参数等方式以达到对应的变化（相当于重载了吧）

## 几种注入方式
1. 字段注入/成员注入
2. setter注入
3. constructor注入

POJO、Bean

- 依靠@ComponentScan指定扫描的包，默认只会扫描当前入口类同级及其子集的package

· 对于实现了同一个接口的多个类，可以通过打上primary选择优先类(此方式更适用于多人协作和库开发)
· 更好地方式是使用条件注解


@ConfigurationProperties
- 读取配置文件中的指定key-value并自动赋值给类的成员变量


## 条件注解@Conditional
- 编写自定义条件注解

## 内置的成品条件组件
- 组件有很多，有需要的时候可以再搜完全版
1. @ConditionOnProperty 配置文件中匹配指定属性
2. @ConditionOnBean     容器中存在指定bean
3. ConditionOnBean           不  

##自动配置

1. 自动配置的原理
2. 为什么要有自动配置 

` 以linui为例 1. 引入  npm 安装/copy 2. 引入组件/函数/class `

- EnableAutoConfiguration 是自动配置的关键，用于使第三方SDK，尤其是maven引入的依赖，变成bean加入容器

spring.factories用于指定本sdk需要加载的Configuration

- EnableXxx模块装配

## SPI机制/思想
`适应变化`
依赖的第三方模块可能有多种实现方案 <br/>

调用方->标准服务接口

基于Interface + 策略模式 + 配置文件

## 解决变化 @Primary @条件注解

#### 自动装配解决的是将bean装入IOC容器的问题，仅仅引入外部类是简单的

## 框架机制
- 不直接使用框架，要做二次封装/二次开发

## 全局异常反馈
#### UnifyResponse 统一错误相应
```json
{
"code":10001, 
"msg": "ok",
"request": "GET+url"
}
```

#### 拦截异常
定义拦截异常的全局类
为什么不直接该抛出异常为调用异常处理类的方法呢？
1. 只能收集我们主动抛出的异常对于jvm抛出的其他异常则无能为力，显得麻烦
2. 这样耦合性极高

Error
Exception

CheckException      编译期异常
RuntimeException    运行时异常

异常处理按照逻辑分为已知和未知异常
未知异常不宜返回e的msg，对于前端没有意义，模糊地提示存在错误即可
已知异常几乎等同于message，需要返回

Spring中可以直接返回类，Spring会进行序列化，但是需要保证有public修饰的获取途径(比如getter)

## 控制返回码等各种返回数据
- ResponseEntity类!!

异常处理流程，面对新的异常先建立异常类，继承HttpException
全局异常处理会捕获，并且根据code码找到自定义properties文件中的message

我们添加的全局异常处理和自动前缀添加其实算式自己开发的增强功能

## 乱码问题的解决
1. 可能没有指定charset为utf-8(但是spring默认为utf-8);
2. 本地文件的编码错误(比如properties的编码)

## 参数校验
- 参数校验极为重要
1. 参数校验代码应与控制器分离(业务逻辑也应该脱离控制器)
2. 参数来源：
    1. GET参数
        1. 问号参数
        2. URL参数
    2. POST参数
3. 校验方式 Bean Validation

在类上打上注解：Validated才可以继续使用参数校验
#### Validated和Valid的异同
1. 他们都可以开启校验，Validated是Spring对java Valid的扩展
2. 建议的使用方式是：
    1. 开启验证使用Validated
    2. 级联使用Valid
    3. 其他情况使用Validated

#### 自定义校验方式

    

## lombok
1. getter、setter、Data
`data注解除了会生成getter和setter外，还会生成equals,hashCode,toString等方法`
2. AllArgsConstructor、NoArgsConstructor
生成构造函数
RequiredArgsConstructor 生成只有非空字段的构造函数
`注意，有参构造函数生成后，默认的无参构造就会消失，所以有NoArgsConstructor`
3. 字段注解 
    1. NonNull 字段不能为null，否则会抛出异常
    ···
4. builder
```
PersonDTO dto = PersonDTO.builder()
                .name("tlb")
                .age(20)
                .build();
```
- builder的坑:
1. 使用了builder之后就不能再使用构造方法了
    - 其实是builder注解会生成一个private的构造函数，可以手动写一个public构造避免这样的问题
2. 如果使用builder模式，而该类又需要当做bean返回到前端，那么还要加上@Getter注解才能序列化
3. Entity上加上@Builder，会默认为类加上全参构造函数，且提供以建造器模式构造对象的方法。但此时因为显示声明了构造器，默认的无参构造器就失效了，就不能通过new Obj()的方式创建对象。这是自然想到加@NoArgsConstructor注解生成无参构造函数以便使用new Obj()方式创建对象，很多框架中都需要反射调用无参构造函数。但是如果显式声明了@NoArgsConstructor，lombok就不会生成全参构造函数，而@Builder中会用到全参构造函数，所以冲突。
   `再加上@AllArgsConstructor可解决。
   @Builder
   @NoArgsConstructor
   @AllArgsConstructor
   同时使用`

## JSR Java Specification Requests
lombok: JSR-269
Bean Validation: JSR-303

##分层
- 目的：在微服务出现之前，大型项目每一层都由不同的开发者和团队开发，使每一层同步开发互不打扰，水平分隔(微服务是垂直分隔的)

规范要求层与层之间应当面向Interface连接，而不应该面向具体
但是，有时候显得没必要，反而制造了很多冗余，只有策略模式在实践中更适合这样的模式
所以只有BannerService使用了接口+实现方式，其他的service没有使用

## 三层结构
APIs->          controller层
service->       service层
repository->    repository层



## JPA
`简单查询很方便`
1. 数据表如何创建
    1. 可视化工具
    2. 手写SQL语句
    3. module 模型类
``yml形式配置文件``
- 一般不应该生成物理外键，而是用软性的逻辑外键
- 配置实体与实体之间的关系，是JPA高效的关键
2. 数据表如何查询
    1. JPA默认为懒加载，而不是急加载
## ORM

- 核心是用实体entity来操作/表达数据表

## 数据库表与表之间的关系
1对1：
1. 查询效率 一个表字段过多，拆分
2. 业务逻辑方面，拆分

1对多

多对多：
- 中间表 中间表是否有意义

单向/双向
注意，双向时，外键JoinColumn是应该打到多端(维护端)的，在一端，被维护端的OneToMany(mappedBy = "banner")，其中字符串应该传入many端的导航属性的名字


## 数据库设计
- 思路：把表当做entity来思考
1. 找到业务对象
    Coupon 表
    Order  表
    Banner 表
    User   表

2. 对象与对象之间的关系
联系：外键

3. 细化
字段、限制、小数点、索引

4. 性能优化
一张表数据条数不宜过多：
    索引
    水平分表
    字段数量 垂直分表(1:1)
    查询方式
    缓存 利用缓存减少直接查询数据库(最重要而明显的方式，数据库设计完之后不宜更改数据表本身了，尤其是老项目更不宜动数据库，所以避免少查询数据库才是王道)
    

## 使用命令启动Spring
部署：maven命令行 mvn clean package
maven视图
会生成uber jar 超级jar包，内置tomcat服务器

java xxx.jar
java -jar xxx.jar --spring.profiles.active=dev
更换环境，甚至不需要修改配置文件


