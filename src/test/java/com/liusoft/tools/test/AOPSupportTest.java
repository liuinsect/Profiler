package com.liusoft.tools.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-9
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public class AOPSupportTest  {


    public static  void main(String[] fasdf) throws InterruptedException {
        ApplicationContext appContext = null;
        try {
            long start = System.currentTimeMillis();
            System.out.println("正在加载配置文件...");

            appContext = new ClassPathXmlApplicationContext("spring-config.xml");//初始化spring 容器});
            System.out.println("配置文件加载完毕,耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AOPSupportBean bean = (AOPSupportBean) appContext.getBean("aopSupportBean");
        bean.test1();
    }

}
