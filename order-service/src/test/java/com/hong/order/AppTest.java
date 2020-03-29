package com.hong.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest{

    @Test
    public void test(){
        LocalDateTime date = LocalDateTime.now();
        System.out.println(date);
        System.out.println(date.plusMinutes(1));
    }

}
