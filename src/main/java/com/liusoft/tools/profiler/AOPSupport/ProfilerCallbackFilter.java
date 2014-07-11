package com.liusoft.tools.profiler.AOPSupport;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>Profile回调函数实现类</p>
 * <p>用于判断哪些方法需要回调</p>
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-10
 * Time: 下午9:02
 * To change this template use File | Settings | File Templates.
 */
public class ProfilerCallbackFilter implements CallbackFilter {

    private String[] monitorMethod;

    public ProfilerCallbackFilter(String[] monitorMethod){
        this.monitorMethod = monitorMethod;
    }

    /**
     * 1 ：使用
     * 0：不使用
     * @param method
     * @return
     */
    @Override
    public int accept(Method method) {
        String methodName = method.getName();
        if( isInArray(methodName) ){
           return 1;
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *是否在数组中
     * @param methodName
     * @return
     */
    private boolean isInArray(  String methodName ){
        for(String mm:monitorMethod){
            if( mm.equals(methodName) ){
                return true;
            }
        }
        return  false;
    }

}
