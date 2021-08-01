package com.github.codingob.jdbc.service;

import com.github.codingob.jdbc.entity.Entity;
import com.github.codingob.jdbc.repository.EntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class EntityService {
    @Resource
    private EntityRepository entityRepository;

    @Transactional(rollbackFor = Exception.class)
    public Entity create(Entity entity){
        return entityRepository.save(entity);
    }
}
