# Spring Data Jdbc

[文档](https://docs.spring.io/spring-data/jdbc/docs/2.2.4/reference/html/#reference)

依赖

```xml

<dependencies>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
    </dependency>
</dependencies>
```

jdbc.properties

```properties
jdbc.driverClassName=org.h2.Driver
jdbc.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false
jdbc.username=sa
jdbc.password=
```

jdbc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jdbc="http://www.springframework.org/schema/jdbc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/jdbc
       http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

    <jdbc:embedded-database type="H2" database-name="test">
        <jdbc:script location="classpath:entity.sql"/>
    </jdbc:embedded-database>

    <context:property-placeholder location="classpath:jdbc.properties"/>

    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <constructor-arg ref="dataSource"/>
    </bean>

    <bean id="server" class="org.h2.tools.Server" factory-method="createWebServer" init-method="start"
          destroy-method="stop">
        <constructor-arg value="-web,-webAllowOthers,-webPort,9092,-tcp,-tcpAllowOthers,-tcpPort,8082"/>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg name="dataSource" ref="dataSource"/>
    </bean>

</beans>
```

sql

```sql
CREATE TABLE ENTITY
(
    ID          BIGINT         NOT NULL AUTO_INCREMENT,
    NAME        VARCHAR(16)    NOT NULL,
    GENDER      TINYINT        NOT NULL,
    MONEY       DECIMAL(10, 2) NOT NULL,
    CREATE_TIME datetime       NOT NULL,
    DELETE      TINYINT        NOT NULL,
    CONSTRAINT ENTITY_PK PRIMARY KEY (ID)
);
```

entity

```java

@Data
@NoArgsConstructor
public class Entity {
    private Integer id;
    private String name;
    private boolean gender;
    private BigDecimal money;
    private Timestamp createTime;
    private boolean delete;

    public Entity(String name, boolean gender) {
        this.name = name;
        this.gender = gender;
    }
}

```

dao

```java

@Repository
public class EntityDao {
    private String sql;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(Entity entity) {
        sql = "INSERT INTO Entity(NAME,GENDER,MONEY,CREATE_TIME,DELETE) VALUES(?,?,?,?,?)";
        return jdbcTemplate.update(sql, entity.getName(), entity.isGender(), new BigDecimal(1000), new Timestamp(System.currentTimeMillis()), entity.isDelete());
    }

    public Entity getById(int id) {
        sql = "SELECT * FROM ENTITY WHERE ID=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Entity.class), id);
    }

    public List<Entity> getByList(int page, int size) {

        sql = "SELECT * FROM ENTITY LIMIT ?,?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Entity.class), (page - 1) * size, size);
    }

    public int updateById(Entity entity) {
        sql = "UPDATE ENTITY SET NAME=?,GENDER=?,MONEY=? WHERE ID=?";
        return jdbcTemplate.update(sql, entity.getName(), entity.isGender(), entity.getMoney(), entity.getId());
    }

    public int deleteById(int id) {
        sql = "DELETE FROM ENTITY WHERE ID=?";
        return jdbcTemplate.update(sql, id);
    }
}
```

Application

```java
public class DataJdbcApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(DataJdbcApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:context.xml");
        context.registerShutdownHook();

        EntityDao entityDao = context.getBean("entityDao", EntityDao.class);

        Entity entity = new Entity("spring_jdbc", false);
        int i = entityDao.create(entity);
        LOGGER.info("插入成功: " + i);

        entity = entityDao.getById(1);
        LOGGER.info("查询成功: " + entity);

        List<Entity> entities = entityDao.getByList(1, 10);
        LOGGER.info("查询成功: " + entities);

        entity.setMoney(new BigDecimal(2000));
        i = entityDao.updateById(entity);
        LOGGER.info("修改成功: " + i);

        i = entityDao.deleteById(1);
        LOGGER.info("删除成功: " + i);
    }
}
```