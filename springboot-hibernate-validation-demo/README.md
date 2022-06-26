> 转载：https://www.cnblogs.com/Chenjiabing/p/13890384.html
>
> ## 什么是 JSR-303？
>
> `JSR-303` 是 `JAVA EE 6` 中的一项子规范，叫做 `Bean Validation`。
>
> `Bean Validation` 为 `JavaBean` 验证定义了相应的`元数据模型`和`API`。缺省的元数据是`Java Annotations`，通过使用 `XML` 可以对原有的元数据信息进行覆盖和扩展。在应用程序中，通过使用`Bean Validation` 或是你自己定义的 `constraint`，例如 `@NotNull`, `@Max`, `@ZipCode` ， 就可以确保数据模型（`JavaBean`）的正确性。`constraint` 可以附加到字段，`getter` 方法，类或者接口上面。对于一些特定的需求，用户可以很容易的开发定制化的 `constraint`。`Bean Validation` 是一个运行时的数据验证框架，在验证之后验证的错误信息会被马上返回。
>
> ## 添加依赖
>
> Spring Boot整合JSR-303只需要添加一个`starter`即可，如下：
>
> ```xml
> <dependency>
>     <groupId>org.springframework.boot</groupId>
>    <artifactId>spring-boot-starter-validation</artifactId>
> </dependency>
> ```
>
> ## 内嵌的注解有哪些？
>
> `Bean Validation` 内嵌的注解很多，基本实际开发中已经够用了，注解如下：
>
> | 注解                        | 详细信息                                                 |
> | :-------------------------- | :------------------------------------------------------- |
> | @Null                       | 被注释的元素必须为 null                                  |
> | @NotNull                    | 被注释的元素必须不为 null                                |
> | @AssertTrue                 | 被注释的元素必须为 true                                  |
> | @AssertFalse                | 被注释的元素必须为 false                                 |
> | @Min(value)                 | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 |
> | @Max(value)                 | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 |
> | @DecimalMin(value)          | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 |
> | @DecimalMax(value)          | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 |
> | @Size(max, min)             | 被注释的元素的大小必须在指定的范围内                     |
> | @Digits (integer, fraction) | 被注释的元素必须是一个数字，其值必须在可接受的范围内     |
> | @Past                       | 被注释的元素必须是一个过去的日期                         |
> | @Future                     | 被注释的元素必须是一个将来的日期                         |
> | @Pattern(value)             | 被注释的元素必须符合指定的正则表达式                     |
>
> > 以上是`Bean Validation`的内嵌的注解，但是`Hibernate Validator`在原有的基础上也内嵌了几个注解，如下。
>
> | 注解      | 详细信息                               |
> | :-------- | :------------------------------------- |
> | @Email    | 被注释的元素必须是电子邮箱地址         |
> | @Length   | 被注释的字符串的大小必须在指定的范围内 |
> | @NotEmpty | 被注释的字符串的必须非空               |
> | @Range    | 被注释的元素必须在合适的范围内         |
>
> ## 如何使用？
>
> 参数校验分为**简单校验**、**嵌套校验**、**分组校验**。
>
> ### **简单校验**
>
> 简单的校验即是没有嵌套属性，直接在需要的元素上标注约束注解即可。如下：
>
> ```less
> @Data
> public class ArticleDTO {
> 
>     @NotNull(message = "文章id不能为空")
>     @Min(value = 1,message = "文章ID不能为负数")
>     private Integer id;
> 
>     @NotBlank(message = "文章内容不能为空")
>     private String content;
> 
>     @NotBlank(message = "作者Id不能为空")
>     private String authorId;
> 
>     @Future(message = "提交时间不能为过去时间")
>     private Date submitTime;
> }
> ```
>
> > 同一个属性可以指定多个约束，比如`@NotNull`和`@MAX`,其中的`message`属性指定了约束条件不满足时的提示信息。
>
> 以上约束标记完成之后，要想完成校验，需要在`controller`层的接口标注`@Valid`注解以及声明一个`BindingResult`类型的参数来接收校验的结果。
>
> 下面简单的演示下添加文章的接口，如下：
>
> ```typescript
> /**
>      * 添加文章
>      */
>     @PostMapping("/add")
>     public String add(@Valid @RequestBody ArticleDTO articleDTO, BindingResult bindingResult) throws JsonProcessingException {
>         //如果有错误提示信息
>         if (bindingResult.hasErrors()) {
>             Map<String , String> map = new HashMap<>();
>             bindingResult.getFieldErrors().forEach( (item) -> {
>                 String message = item.getDefaultMessage();
>                 String field = item.getField();
>                 map.put( field , message );
>             } );
>             //返回提示信息
>             return objectMapper.writeValueAsString(map);
>         }
>         return "success";
>     }
> ```
>
> > 仅仅在属性上添加了约束注解还不行，还需在接口参数上标注`@Valid`注解并且声明一个`BindingResult`类型的参数来接收校验结果。
>
> ### **分组校验**
>
> 举个栗子：上传文章不需要传文章`ID`，但是修改文章需要上传文章`ID`，并且用的都是同一个`DTO`接收参数，此时的约束条件该如何写呢？
>
> 此时就需要对这个文章`ID`进行分组校验，上传文章接口是一个分组，不需要执行`@NotNull`校验，修改文章的接口是一个分组，需要执行`@NotNull`的校验。
>
> > 所有的校验注解都有一个`groups`属性用来指定分组，`Class<?>[]`类型，没有实际意义，因此只需要定义一个或者多个接口用来区分即可。
>
> ```less
> @Data
> public class ArticleDTO {
> 
>     /**
>      * 文章ID只在修改的时候需要检验，因此指定groups为修改的分组
>      */
>     @NotNull(message = "文章id不能为空",groups = UpdateArticleDTO.class )
>     @Min(value = 1,message = "文章ID不能为负数",groups = UpdateArticleDTO.class)
>     private Integer id;
> 
>     /**
>      * 文章内容添加和修改都是必须校验的，groups需要指定两个分组
>      */
>     @NotBlank(message = "文章内容不能为空",groups = {AddArticleDTO.class,UpdateArticleDTO.class})
>     private String content;
> 
>     @NotBlank(message = "作者Id不能为空",groups = AddArticleDTO.class)
>     private String authorId;
> 
>     /**
>      * 提交时间是添加和修改都需要校验的，因此指定groups两个
>      */
>     @Future(message = "提交时间不能为过去时间",groups = {AddArticleDTO.class,UpdateArticleDTO.class})
>     private Date submitTime;
>     
>     //修改文章的分组
>     public interface UpdateArticleDTO{}
> 
>     //添加文章的分组
>     public interface AddArticleDTO{}
> 
> }
> ```
>
> > JSR303本身的`@Valid`并不支持分组校验，但是Spring在其基础提供了一个注解`@Validated`支持分组校验。`@Validated`这个注解`value`属性指定需要校验的分组。
>
> ```typescript
> /**
>      * 添加文章
>      * @Validated：这个注解指定校验的分组信息
>      */
>     @PostMapping("/add")
>     public String add(@Validated(value = ArticleDTO.AddArticleDTO.class) @RequestBody ArticleDTO articleDTO, BindingResult bindingResult) throws JsonProcessingException {
>         //如果有错误提示信息
>         if (bindingResult.hasErrors()) {
>             Map<String , String> map = new HashMap<>();
>             bindingResult.getFieldErrors().forEach( (item) -> {
>                 String message = item.getDefaultMessage();
>                 String field = item.getField();
>                 map.put( field , message );
>             } );
>             //返回提示信息
>             return objectMapper.writeValueAsString(map);
>         }
>         return "success";
>     }
> ```
>
> ### **嵌套校验**
>
> 嵌套校验简单的解释就是一个实体中包含另外一个实体，并且这两个或者多个实体都需要校验。
>
> 举个栗子：文章可以有一个或者多个分类，作者在提交文章的时候必须指定文章分类，而分类是单独一个实体，有`分类ID`、`名称`等等。大致的结构如下：
>
> ```cpp
> public class ArticleDTO{
>   ...文章的一些属性.....
>   
>   //分类的信息
>   private CategoryDTO categoryDTO;
> }
> ```
>
> 此时文章和分类的属性都需要校验，这种就叫做嵌套校验。
>
> > 嵌套校验很简单，只需要在嵌套的实体属性标注`@Valid`注解，则其中的属性也将会得到校验，否则不会校验。
>
> 如下**文章分类实体类校验**：
>
> ```less
> /**
>  * 文章分类
>  */
> @Data
> public class CategoryDTO {
>     @NotNull(message = "分类ID不能为空")
>     @Min(value = 1,message = "分类ID不能为负数")
>     private Integer id;
> 
>     @NotBlank(message = "分类名称不能为空")
>     private String name;
> }
> ```
>
> 文章的实体类中有个嵌套的文章分类`CategoryDTO`属性，需要使用`@Valid`标注才能嵌套校验，如下：
>
> ```less
> @Data
> public class ArticleDTO {
> 
>     @NotBlank(message = "文章内容不能为空")
>     private String content;
> 
>     @NotBlank(message = "作者Id不能为空")
>     private String authorId;
> 
>     @Future(message = "提交时间不能为过去时间")
>     private Date submitTime;
> 
>     /**
>      * @Valid这个注解指定CategoryDTO中的属性也需要校验
>      */
>     @Valid
>     @NotNull(message = "分类不能为空")
>     private CategoryDTO categoryDTO;
>   }
> ```
>
> `Controller`层的添加文章的接口同上，需要使用`@Valid`或者`@Validated`标注入参，同时需要定义一个`BindingResult`的参数接收校验结果。
>
> > 嵌套校验针对**分组查询**仍然生效，如果嵌套的实体类（比如`CategoryDTO`）中的校验的属性和接口中`@Validated`注解指定的分组不同，则不会校验。
>
> `JSR-303`针对`集合`的嵌套校验也是可行的，比如`List`的嵌套校验，同样需要在属性上标注一个`@Valid`注解才会生效，如下：
>
> ```less
> @Data
> public class ArticleDTO {
>     /**
>      * @Valid这个注解标注在集合上，将会针对集合中每个元素进行校验
>      */
>     @Valid
>     @Size(min = 1,message = "至少一个分类")
>     @NotNull(message = "分类不能为空")
>     private List<CategoryDTO> categoryDTOS;
>   }
> ```
>
> > 总结：嵌套校验只需要在需要校验的元素（单个或者集合）上添加`@Valid`注解，接口层需要使用`@Valid`或者`@Validated`注解标注入参。
>
> ## 如何接收校验结果？
>
> 接收校验的结果的方式很多，不过实际开发中最好选择一个优雅的方式，下面介绍常见的两种方式。
>
> ### **BindingResult 接收**
>
> 这种方式需要在`Controller`层的每个接口方法参数中指定，Validator会将校验的信息自动封装到其中。这也是上面例子中一直用的方式。如下：
>
> ```less
> @PostMapping("/add")
>     public String add(@Valid @RequestBody ArticleDTO articleDTO, BindingResult bindingResult){}
> ```
>
> 这种方式的弊端很明显，每个接口方法参数都要声明，同时每个方法都要处理校验信息，显然不现实，舍弃。
>
> > 此种方式还有一个优化的方案：使用`AOP`，在`Controller`接口方法执行之前处理`BindingResult`的消息提示，不过这种方案仍然**不推荐使用**。
>
> ### **全局异常捕捉**
>
> 参数在校验失败的时候会抛出的`MethodArgumentNotValidException`或者`BindException`两种异常，可以在全局的异常处理器中捕捉到这两种异常，将提示信息或者自定义信息返回给客户端。
>
> 全局异常捕捉之前有单独写过一篇文章，不理解的可以看[满屏的try-catch，你不瘆得慌？](https://mp.weixin.qq.com/s/EMmqcdPPfWqHuKRHbct7WA)。
>
> 作者这里就不再详细的贴出其他的异常捕获了，仅仅贴一下参数校验的异常捕获（**仅仅举个例子，具体的返回信息需要自己封装**），如下：
>
> ```java
> @RestControllerAdvice
> public class ExceptionRsHandler {
> 
>     @Autowired
>     private ObjectMapper objectMapper;
> 
>     /**
>      * 参数校验异常步骤
>      */
>     @ExceptionHandler(value= {MethodArgumentNotValidException.class , BindException.class})
>     public String onException(Exception e) throws JsonProcessingException {
>         BindingResult bindingResult = null;
>         if (e instanceof MethodArgumentNotValidException) {
>             bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
>         } else if (e instanceof BindException) {
>             bindingResult = ((BindException)e).getBindingResult();
>         }
>         Map<String,String> errorMap = new HashMap<>(16);
>         bindingResult.getFieldErrors().forEach((fieldError)->
>                 errorMap.put(fieldError.getField(),fieldError.getDefaultMessage())
>         );
>         return objectMapper.writeValueAsString(errorMap);
>     }
> 
> }
> ```
>
> ## spring-boot-starter-validation做了什么？
>
> 这个启动器的自动配置类是`ValidationAutoConfiguration`，最重要的代码就是注入了一个`Validator`（校验器）的实现类，代码如下：
>
> ```java
> @Bean
>  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
>  @ConditionalOnMissingBean(Validator.class)
>  public static LocalValidatorFactoryBean defaultValidator() {
>   LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
>   MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
>   factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
>   return factoryBean;
>  }
> ```
>
> 这个有什么用呢？`Validator`这个接口定义了校验的方法，如下：
>
> ```vbnet
> <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups);
> 
> 
> <T> Set<ConstraintViolation<T>> validateProperty(T object,
>               String propertyName,
>               Class<?>... groups);
>                            
> <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
>               String propertyName,
>               Object value,
>               Class<?>... groups);
> ......
> ```
>
> > 这个`Validator`可以用来自定义实现自己的校验逻辑，有些大公司完全不用JSR-303提供的`@Valid`注解，而是有一套自己的实现，其实本质就是利用`Validator`这个接口的实现。
>
> ## 如何自定义校验？
>
> 虽说在日常的开发中内置的约束注解已经够用了，但是仍然有些时候不能满足需求，需要自定义一些校验约束。
>
> **举个栗子：有这样一个例子，传入的数字要在列举的值范围中，否则校验失败。**
>
> ### **自定义校验注解**
>
> 首先需要自定义一个校验注解，如下：
>
> ```scss
> @Documented
> @Constraint(validatedBy = { EnumValuesConstraintValidator.class})
> @Target({ METHOD, FIELD, ANNOTATION_TYPE })
> @Retention(RUNTIME)
> @NotNull(message = "不能为空")
> public @interface EnumValues {
>     /**
>      * 提示消息
>      */
>     String message() default "传入的值不在范围内";
> 
>     /**
>      * 分组
>      * @return
>      */
>     Class<?>[] groups() default { };
> 
>     Class<? extends Payload>[] payload() default { };
> 
>     /**
>      * 可以传入的值
>      * @return
>      */
>     int[] values() default { };
> }
> ```
>
> 根据`Bean Validation API` 规范的要求有如下三个属性是必须的：
>
> 1. `message`：定义消息模板，校验失败时输出
> 2. `groups`：用于校验分组
> 3. `payload`：`Bean Validation API` 的使用者可以通过此属性来给约束条件指定严重级别. 这个属性并不被API自身所使用。
>
> 除了以上三个必须要的属性，添加了一个`values`属性用来接收限制的范围。
>
> 该校验注解头上标注的如下一行代码：
>
> ```python
> @Constraint(validatedBy = { EnumValuesConstraintValidator.class})
> ```
>
> 这个`@Constraint`注解指定了通过哪个校验器去校验。
>
> > 自定义校验注解可以复用内嵌的注解，比如`@EnumValues`注解头上标注了一个`@NotNull`注解，这样`@EnumValues`就兼具了`@NotNull`的功能。
>
> ### **自定义校验器**
>
> `@Constraint`注解指定了校验器为`EnumValuesConstraintValidator`，因此需要自定义一个。
>
> 自定义校验器需要实现`ConstraintValidator<A extends Annotation, T>`这个接口，第一个泛型是`校验注解`，第二个是`参数类型`。代码如下：
>
> ```typescript
> /**
>  * 校验器
>  */
> public class EnumValuesConstraintValidator implements ConstraintValidator<EnumValues,Integer> {
>     /**
>      * 存储枚举的值
>      */
>     private  Set<Integer> ints=new HashSet<>();
> 
>     /**
>      * 初始化方法
>      * @param enumValues 校验的注解
>      */
>     @Override
>     public void initialize(EnumValues enumValues) {
>         for (int value : enumValues.values()) {
>             ints.add(value);
>         }
>     }
> 
>     /**
>      *
>      * @param value  入参传的值
>      * @param context
>      * @return
>      */
>     @Override
>     public boolean isValid(Integer value, ConstraintValidatorContext context) {
>         //判断是否包含这个值
>         return ints.contains(value);
>     }
> }
> ```
>
> > 如果约束注解需要对其他数据类型进行校验，则可以的自定义对应数据类型的校验器，然后在约束注解头上的`@Constraint`注解中指定其他的校验器。
>
> ### **演示**
>
> 校验注解和校验器自定义成功之后即可使用，如下：
>
> ```kotlin
> @Data
> public class AuthorDTO {
>     @EnumValues(values = {1,2},message = "性别只能传入1或者2")
>     private Integer gender;
> }
> ```
>
> ## 总结
>
> 数据校验作为客户端和服务端的一道屏障，有着重要的作用，通过这篇文章希望能够对`JSR-303`数据校验有着全面的认识。