Profiler
========
java实现的一个简单的方法级别的耗时监控工具，可以监控每个方法的运行时间和相对堆栈上层的耗时比例


Version 3
第三版，引入cglib实现对监控方法的透明织入

使用方式：

    <!--声明  aopSupportBean 的 test，1,2,3方法加入监控-->
     <bean id="profilerSpringAOPSupport" class="com.liusoft.tools.profiler.AOPSupport.ProfilerSpringAOPSupport">
          <property name="monitorMethod">
              <map>
                  <entry key="aopSupportBean">
                      <value>test1,test2,test3</value>
                  </entry>
              </map>
          </property>
      </bean>
    <!-- 测试bean ： 普通的一个bean而已  -->
    <bean id="aopSupportBean" class="com.liusoft.tools.test.AOPSupportBean"/>

详见单元测试

                                                                                                        2013-11-15日
