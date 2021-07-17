package com.demo.spring.mybatis.mapper;

import com.demo.spring.mybatis.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author codingob
 */
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
