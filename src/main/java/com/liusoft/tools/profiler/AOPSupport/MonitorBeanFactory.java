package com.liusoft.tools.profiler.AOPSupport;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

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

    /**
     * 解决Bean没有空构造参数的情况
     * 将构造函数需要的参数从IOC容器中获取。
     * @param applicationContext
     * @param proxyClass
     * @param monitorMethod
     * @param beanName
     * @param timeUnitName
     * @return
     */
    public Object getInstance(ApplicationContext applicationContext,
                              Class proxyClass,
                              String[] monitorMethod,
                              String beanName,
                              String timeUnitName){

        AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext)applicationContext;
        BeanDefinition beanDefinition = abstractApplicationContext.getBeanFactory().getBeanDefinition(beanName);
        ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
        List<ConstructorArgumentValues.ValueHolder> valueHolderList =  constructorArgumentValues.getGenericArgumentValues();
        if( valueHolderList.isEmpty() ){
            return getInstance(proxyClass,monitorMethod,timeUnitName);
        }

        Object[] arguments = new Object[valueHolderList.size()];
        TypedStringValue typedStringValue = null;
        for( int i = 0 ; i< valueHolderList.size();i++ ){
            typedStringValue = (TypedStringValue)valueHolderList.get(i).getValue();
            arguments[i]=typedStringValue.getValue();
        }

        Constructor[] constructors = proxyClass.getDeclaredConstructors();
        Constructor suitableConstructor = null;
        for(Constructor constructor : constructors){
            if( valueHolderList.size() == constructor.getParameterCount() ){
                suitableConstructor = constructor;
                break;
            }
        }
//        ((ClassPathXmlApplicationContext) applicationContext).getBeanFactory().getBeanDefinition(beanName).getConstructorArgumentValues().getGenericArgumentValues().get(0).getValue()
        Enhancer enhancer = new Enhancer();

        //进行代理
        enhancer.setSuperclass( proxyClass );
        //使用两个拦截器的原因是 有的方法不做监控，用空拦截器实现，做监控的用ProfilerCGLIBProxy实现，   ProfilerCallbackFilter 中返回的索引，确定用哪个拦截器
        enhancer.setCallbacks( new Callback[]{ NoOp.INSTANCE , new ProfilerCGLIBProxy(timeUnitName) } );
        //生成代理实例
        enhancer.setCallbackFilter( new ProfilerCallbackFilter( monitorMethod ) );

        return enhancer.create(suitableConstructor.getParameterTypes(),arguments);
    }

}
