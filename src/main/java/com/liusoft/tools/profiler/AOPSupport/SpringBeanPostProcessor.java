package com.liusoft.tools.profiler.AOPSupport;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.Object;import java.lang.Override;import java.lang.String;import java.lang.System;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 13-11-4
 * Time: 下午2:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class SpringBeanPostProcessor implements BeanPostProcessor,ApplicationContextAware {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Map<String,String[]> monitorMethodMapping = getMonitorMethodMapping();
        String[] monitorMethod =  monitorMethodMapping.get(beanName);
        if( monitorMethod != null ){//说明有需要监控的方法
            return replaceMonitorBean(bean,beanName);
        }

        return bean;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("targetClass")){
            System.out.println("postProcessAfterInitialization");
        }

        return bean;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public abstract Map<String,String[]> getMonitorMethodMapping();


    protected abstract  Object replaceMonitorBean(Object bean, String beanName);

}
