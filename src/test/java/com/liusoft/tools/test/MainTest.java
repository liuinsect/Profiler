package com.liusoft.tools.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-9
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public class MainTest extends BaseTest  {

    @Test
    public void Spring融合_默认方式_test() throws InterruptedException {
        AOPSupportBean bean = (AOPSupportBean) applicationContext.getBean("aopSupportBean");
        bean.test1();
    }


    @Test
    public void Spring融合_纳秒级_test() throws InterruptedException {
        AOPSupportBean bean = (AOPSupportBean) applicationContext.getBean("aopSupportBean2");
        bean.test1();
    }

    @Test
    public void Spring融合_秒级_test() throws InterruptedException {
        AOPSupportBean bean = (AOPSupportBean) applicationContext.getBean("aopSupportBean3");
        bean.test1();
    }

    @Test
    public void spring融合_无空构造函数_test() throws InterruptedException {
        AOPSupportBean2 bean = (AOPSupportBean2) applicationContext.getBean("aopSupportBeanByCon");
        bean.test1();
    }



}
