package com.example.demo2.service.impl;

import com.example.demo2.dao.UserinfoDao;
import com.example.demo2.model.Userchainrelation;
import com.example.demo2.model.Userinfo;

import com.example.demo2.service.UserinfoService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserinfoServiceImplTest {

    @Autowired
    private UserinfoDao userinfoDao;

    private Userinfo userinfo;

    private Userchainrelation userchainrelation;
    @Autowired
    private UserinfoService userinfoService;
    private UserinfoServiceImpl userinfoServiceImpl;

    @BeforeEach
    void init() {
        userinfo = new Userinfo();
        userchainrelation = new Userchainrelation();
        userinfoServiceImpl = new UserinfoServiceImpl(userinfoDao);
        userchainrelation.setChainId(5);
        userinfo.setNickname("88888888");

    }

    @Test
    public void test2() {
        userinfo.setAddress("test");
        userchainrelation.setAddress("test");
        try {
            userinfoService.insertUserChain(userinfo, userchainrelation);
        }catch (ConstraintViolationException e){
            System.out.println(e.getMessage());
        }
        assertThrows(ConstraintViolationException.class, () -> {
            userinfoService.insertUserChain(userinfo, userchainrelation);
        });
    }
}
