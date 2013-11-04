package com.liusoft.tools.test;

import com.liusoft.tools.profiler.Profiler;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-1
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 * 2-1->1
 * 2-2->1
 * 3->1
 */
public class App2 {

    @Test
    public void test_制造多层嵌套_并包含当前层次多次调用(){
        test_第一层();
    }

    public void test_第一层(){
        Profiler.enter("第一层");
        test_第二层();
        Profiler.release();
    }

    public void test_第二层(){
        test_第二层_1();

        test_第二层_2();

    }

    public void test_第二层_1(){
        Profiler.enter("第二层_1");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Profiler.release();
    }

    public void test_第二层_2(){
        Profiler.enter("第二层_2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Profiler.release();

        test_第三层_3();
    }

    public void test_第三层_3(){
        Profiler.enter("第二层_3");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Profiler.release();
    }



}
