# Spring-Mybatis

数据库: MySql

版本: 8.0.25

Spring: 5.3.9

mybatis: 3.5.7

pagehelper: 5.2.1

## 导入依赖

1. `mysql-connector-java` :mysql驱动
2. `mybatis`: mybatis
3. `mybatis-spring`: mybatis-spring
4. `spring-jdbc`: spring
5. `spring-context`: spring
6. `spring-batch-infrastructure`: spring
7. `pagehelper`: 分页插件

## 配置数据库连接信息

mybatis.properties

```properties
mybatis.driverClassName=com.mysql.cj.jdbc.Driver
mybatis.url=jdbc:mysql://localhost:3306/mybatis
mybatis.username=root
mybatis.password=root
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.map-underscore-to-camel-case=true
```

## 配置Mybatis

```java

@PropertySource("classpath:mybatis.properties")
@MapperScan("com.demo.spring.mybatis.mapper")
@ComponentScan("com.demo.spring.mybatis")
@EnableTransactionManagement
public class MybatisConfig {
    @Value("${mybatis.driverClassName}")
    private String driverClassName;
    @Value("${mybatis.url}")
    private String url;
    @Value("${mybatis.username}")
    private String username;
    @Value("${mybatis.password}")
    private String password;
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    @Value("${mybatis.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;

    @Bean
    public DataSource dataSource() {
        return new PooledDataSource(driverClassName, url, username, password);
    }

    /**
     * 加载Mapper.xml
     * @return Resource
     * @throws IOException IO异常
     */
    @Bean
    public Resource[] resources() throws IOException {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources(mapperLocations);
    }

    /**
     * 加载Mybatis配置信息
     * @return Configuration
     */
    @Bean
    public org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        return configuration;
    }

    /**
     * 分页插件
     * @return Interceptor
     */
    @Bean
    public Interceptor pagePlugin() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("params", "mysql");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    /**
     * 插件配置
     * @param interceptors 插件
     * @return Interceptor[]
     */
    @Bean
    public Interceptor[] interceptors(Interceptor... interceptors) {
        return interceptors;
    }

    /**
     * 创建工厂SqlSessionFactory
     * @return SqlSessionFactory
     * @throws Exception Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource,
                                               Resource[] resources,
                                               Configuration configuration,
                                               Interceptor[] interceptors
    ) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(resources);
        factoryBean.setConfiguration(configuration);
        factoryBean.setPlugins(interceptors);
        return factoryBean.getObject();
    }

    /**
     * 交由Spring容器管理事务
     * @return transactionManager
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

## 创建数据库

执行[mybatis.sql](../docker/mybatis/db/mybatis.sql)数据库脚本初始化

mybatis.sql

```sql
DROP DATABASE IF EXISTS `mybatis`;
USE `mybatis`;
DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity`
(
    `id`          int                                                          NOT NULL AUTO_INCREMENT,
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `gender`      tinyint                                                      NULL DEFAULT 1,
    `money`       decimal(10, 0) UNSIGNED                                      NULL DEFAULT 0,
    `create_time` datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

## 创建实体

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entity implements Serializable {
    private Integer id;
    private String name;
    private Boolean gender;
    private BigDecimal money;
    private Timestamp createTime;
}
```

## 创建Mapper接口

```java

@Mapper
@Repository
public interface EntityMapper {
    /**
     * 插入数据
     * @param entity 实体
     * @return 插入条数
     */
    int create(Entity entity);

    /**
     * 删除数据
     * @param id 实体Id
     * @return 删除条数
     */
    int deleteById(@Param("id") int id);

    /**
     * 获取单条数据
     * @param id 实体Id
     * @return Entity
     */
    Entity getById(@Param("id") int id);

    /**
     * 分页
     * @return Entity
     */
    List<Entity> getListByPage();

    /**
     * 更新数据
     * @param entity 实体
     * @return 更新条数
     */
    int update(Entity entity);
}
```

## Mapper映射

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.demo.spring.mybatis.mapper.EntityMapper">

    <insert id="create" parameterType="com.demo.spring.mybatis.entity.Entity" keyProperty="id" keyColumn="id">
        insert into entity(id, name, gender)
        VALUES (#{id}, #{name}, #{gender})
    </insert>

    <update id="update" parameterType="com.demo.spring.mybatis.entity.Entity">
        update entity
        set name=#{name},
        gender=#{gender},
        money=#{money}
        where id = #{id}
    </update>

    <delete id="deleteById" parameterType="int">
        delete
        from entity
        where id = #{id}
    </delete>

    <select id="getById" resultType="com.demo.spring.mybatis.entity.Entity">
        select *
        from entity
        where id = #{id}
    </select>

    <select id="getListByPage" resultType="com.demo.spring.mybatis.entity.Entity">
        select *
        from entity
    </select>
</mapper>
```

## 事务&分页(Service)

```java

@Service
public class EntityService {
    /**
     * 模拟触发异常Id
     */
    private final static int ERROR_ID = 4;

    @Resource
    private EntityMapper entityMapper;

    /**
     * 初始化数据
     *
     * @param id    账户Id
     * @param money 初始金额
     * @return 插入条数
     */
    @Transactional(rollbackFor = Exception.class)
    public int initial(int id, BigDecimal money) {
        Entity entity = new Entity();
        entity.setId(id);
        entity.setName("entity" + id);
        entity.setGender(false);
        entity.setMoney(money);
        entityMapper.create(entity);
        return entityMapper.update(entity);
    }

    /**
     * 获取当前账户金额
     *
     * @param id 账号Id
     * @return 当前账号金额
     */
    public BigDecimal getMoneyById(int id) {
        return entityMapper.getById(id).getMoney();
    }

    /**
     * 转账
     *
     * @param id1   转出账号Id
     * @param id2   转入账户Id
     * @param money 转账金额
     */
    @Transactional(rollbackFor = Exception.class)
    public void transfer(int id1, int id2, BigDecimal money) {
        Entity entity1 = entityMapper.getById(id1);
        Entity entity2 = entityMapper.getById(id2);
        entity1.setMoney(entity1.getMoney().subtract(money));
        entityMapper.update(entity1);
        if (id1 == ERROR_ID || id2 == ERROR_ID) {
            throw new RuntimeException("转账异常");
        }
        entity2.setMoney(entity2.getMoney().add(money));
        entityMapper.update(entity2);
    }

    /**
     * 清空账号
     *
     * @param id 账号Id
     * @return 删除条数
     */
    @Transactional(rollbackFor = Exception.class)
    public int clear(int id) {
        return entityMapper.deleteById(id);
    }

    /**
     * 分页
     * @param page 当前页
     * @param size 页大小
     * @return PageInfo
     */
    public PageInfo<Entity> getListByPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Entity> entities = entityMapper.getListByPage();
        return new PageInfo<>(entities, size);
    }
}
```

## 测试

### Mapper测试类

```java

@SpringJUnitConfig(MybatisConfig.class)
@Slf4j
public class EntityMapperTest {
    private final static int ID = 1;
    private final static int PAGE = 1;
    private final static int SIZE = 10;

    @Autowired
    private EntityMapper entityMapper;

    @Test
    void create() {
        Entity entity = new Entity();
        entity.setId(ID);
        entity.setName("insert");
        log.info(entity.toString());
        int i = entityMapper.create(entity);
        Assertions.assertEquals(1, i);
    }

    @Test
    void delete() {
        int i = entityMapper.deleteById(1);
        Assertions.assertEquals(1, i);
    }

    @Test
    void update() {
        Entity entity = entityMapper.getById(ID);
        log.info("修改前: " + entity);
        entity.setName("update");
        int i = entityMapper.update(entity);
        log.info("修改后: " + entityMapper.getById(ID));
        Assertions.assertEquals(1, i);
    }

    @Test
    void getById() {
        Entity entity = entityMapper.getById(ID);
        log.info(entity.toString());
        Assertions.assertNotNull(entity);
    }

    @Test
    void getList() {
        List<Entity> entityList = entityMapper.getList((PAGE - 1) * SIZE, SIZE);
        log.info("当前第 " + PAGE + " 页,每页 " + SIZE + " 条: " + entityList);
        Assertions.assertEquals(1, entityList.size());
    }
}
```

### Service测试类

```java

@SpringJUnitConfig(MybatisConfig.class)
@Slf4j
public class EntityServiceTest {
    @Autowired
    private EntityService entityService;
    private final static int ID1 = 3;
    private final static int ID2 = 5;
    private final static BigDecimal MONEY = new BigDecimal(1000);
    private final static BigDecimal TRANSFER_MONEY = new BigDecimal(100);

    @Test
    void initial() {
        int i;
        i = entityService.initial(ID1, MONEY);
        Assertions.assertEquals(1, i);
        i = entityService.initial(ID2, MONEY);
        Assertions.assertEquals(1, i);
    }

    @Test
    void transfer() {
        BigDecimal money1 = entityService.getMoneyById(ID1);
        BigDecimal money2 = entityService.getMoneyById(ID2);
        log.info(ID1 + " 转账前: " + money1);
        log.info(ID2 + " 转账前: " + money2);
        try {
            entityService.transfer(ID1, ID2, TRANSFER_MONEY);
            money1 = entityService.getMoneyById(ID1);
            money2 = entityService.getMoneyById(ID2);
            Assertions.assertEquals(MONEY.subtract(TRANSFER_MONEY), money1);
            Assertions.assertEquals(MONEY.add(TRANSFER_MONEY), money2);
        } catch (Exception e) {
            Assertions.assertEquals(MONEY, money1);
            Assertions.assertEquals(MONEY, money2);
            e.printStackTrace();
        }
        log.info(ID1 + " 转账后: " + money1);
        log.info(ID2 + " 转账后: " + money2);
    }

    @Test
    void clear() {
        int i;
        i = entityService.clear(ID1);
        Assertions.assertEquals(1, i);
        i = entityService.clear(ID2);
        Assertions.assertEquals(1, i);
    }

    @Test
    void getListByPage() {
        for (int i = 1; i < 21; i++) {
            entityService.initial(i, new BigDecimal(1000));
        }
        PageInfo<Entity> listByPage = entityService.getListByPage(1, 10);
        log.info(listByPage.getList().toString());
        Assertions.assertEquals(20, listByPage.getTotal());
        for (int i = 1; i < 21; i++) {
            entityService.clear(i);
        }
    }
}
```