package com.liusoft.tools.test;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 14-4-8
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest {

    public static ApplicationContext applicationContext = null;

    @BeforeClass
    public  static void init(){
        try {
            long start = System.currentTimeMillis();
            System.out.println("正在加载配置文件...");

            applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");//初始化spring 容器});
            System.out.println("配置文件加载完毕,耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
