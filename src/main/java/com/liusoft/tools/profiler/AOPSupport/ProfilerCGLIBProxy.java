package com.liusoft.tools.profiler.AOPSupport;



import com.liusoft.tools.profiler.Profiler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-9
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public class ProfilerCGLIBProxy implements MethodInterceptor {


    @Override
    public Object intercept(Object target, Method method, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
        //用户进行判断
        //TODO 再完善一下打印的信息 最好能过滤出类名和方法名出来
        Object result =  null;

//        class com.liusoft.tools.test.AOPSupportBean$$EnhancerByCGLIB$$ef560001test3
        String proxyCalss = target.getClass().getName();


        Profiler.enter( proxyCalss.split("\\$\\$")[0] +"."+ method.getName() );
        result = methodProxy.invokeSuper(target, methodParams);
        Profiler.release();
        return result;
    }
}

