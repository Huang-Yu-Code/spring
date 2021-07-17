package com.demo.spring.mybatis.service;

import com.demo.spring.mybatis.entity.Entity;
import com.demo.spring.mybatis.mapper.EntityMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 事务-转账
 *
 * @author codingob
 */
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
