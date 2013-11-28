package com.liusoft.tools.test;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-9
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
public class AOPSupportBean {


    private  DependencyBean dependencyBean;

    public void test1() throws InterruptedException {
        System.out.println("进入业务方方法");
        test2();
        test3();
    }


    public void test2() throws InterruptedException{
        Thread.sleep(300);
    }

    public void test3() throws InterruptedException{
        Thread.sleep(200);
        test4();
    }


    public void test4() throws InterruptedException{
        Thread.sleep(400);
    }

    public void setDependencyBean(DependencyBean dependencyBean) {
        this.dependencyBean = dependencyBean;
    }
}
