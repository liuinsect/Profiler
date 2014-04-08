package com.liusoft.tools.profiler.AOPSupport;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * 动态代理类生成工厂
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-9
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class MonitorBeanFactory {

    public Object getInstance(Class proxyClass , String[] monitorMethod,String timeUnitName){
        Enhancer enhancer = new Enhancer();

        //进行代理
        enhancer.setSuperclass( proxyClass );
        //使用两个拦截器的原因是 有的方法不做监控，用空拦截器实现，做监控的用ProfilerCGLIBProxy实现，   ProfilerCallbackFilter 中返回的索引，确定用哪个拦截器
        enhancer.setCallbacks( new Callback[]{ NoOp.INSTANCE , new ProfilerCGLIBProxy(timeUnitName) } );
        //生成代理实例
        enhancer.setCallbackFilter( new ProfilerCallbackFilter( monitorMethod ) );
        return enhancer.create();
    }

}
