package com.pinyougou.user.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/name")
    public Map showName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();//得到登陆人账号
        Map map=new HashMap<>();
        map.put("loginName", name);
        return map;
    }

    @RequestMapping("/getAllBeans")
    public void getAllBeans() {
        RedisTemplate redisTemplate = (RedisTemplate) SpringContextHolder.getApplicationContext().getBean("redisTemplate");
        System.out.println(redisTemplate);
    }
}

