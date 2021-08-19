package com.github.codingob.test.service;

import com.github.codingob.test.dao.WebDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class WebService {
    private WebDao dao;

    @Autowired
    public void setDao(WebDao dao) {
        this.dao = dao;
    }

    public void xxxCreate(){
        dao.create();
    }

    public void xxxRead(){
        dao.read();
    }

    public void xxxUpdate(){
        dao.update();
    }

    public void xxxDelete(){
        dao.delete();
    }
}
