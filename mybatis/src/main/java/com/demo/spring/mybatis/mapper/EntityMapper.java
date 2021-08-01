package com.demo.spring.mybatis.mapper;

import com.demo.spring.mybatis.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Mapper
@Repository
public interface EntityMapper {
    /**
     * 插入数据
     *
     * @param entity 实体
     * @return 插入数
     */
    int create(Entity entity);

    /**
     * 物理删除数据
     *
     * @param id ID
     * @return 删除数
     */
    int deleteById(@Param("id") int id);

    /**
     * 逻辑删除数据
     *
     * @param id ID
     * @return 删除数
     */
    int logicalDeleteById(@Param("id") int id);

    /**
     * 查询数据
     *
     * @param id ID
     * @return 单条数据
     */
    Entity getById(@Param("id") int id);

    /**
     * 查询多条数据
     *
     * @return 数据集合
     */
    List<Entity> getListByPage();

    /**
     * 更新数据
     *
     * @param entity 实体
     * @return 更新数
     */
    int update(Entity entity);
}
