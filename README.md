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
2. 
