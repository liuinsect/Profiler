package com.liusoft.tools.profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

/**
 * 监控方法运行时间工具类
 *
 * @author liukunyang
 * @version V3.0
 * @Package com.liusoft
 * @date 2013-9-22 下午01:50:33
 */
public class Profiler {

    /**
     * 日志记录器 使用者必须声明一个叫ProfilerLogger的日志记录器。
     */
    private static Logger log = Logger.getLogger("ProfilerLogger");

    /**
     * 存放各个线程执行栈的情况
     */
    private static ThreadLocal<Stack<InnerProfiler>> threadMap = new ThreadLocal<Stack<InnerProfiler>>();

    /**
     *存放执行堆栈的情况，顺序是从调用层次最深，到条用层次最浅
     * // threadlocal是否需要释放？？ thread 退出的时候会释放
     */
    private static ThreadLocal<List<InnerProfiler>> excuteResultMap = new ThreadLocal<List<InnerProfiler>>();

    /**
     * 真实调用位置距离当前栈顶位置的偏移量
     */
    private static final int pos = 3;

    /**
     * 使用模板模式入口
     *
     * @param callback
     * @author liukunyang
     * @date 2013-9-22
     */
    public static void enter(ProfilerCallBack callback) {
        Profiler.doEnter("");
        callback.excute();
        Profiler.release();
    }

    /**
     * 模板模式和普通模式真正执行的入口方法
     *
     * @author liukunyang
     * @date 2013-9-22
     */
    private static void doEnter(String msg) {
        StackTraceElement[] stArray = Thread.currentThread().getStackTrace();

        Stack<InnerProfiler> stack = threadMap.get();

        if (stack == null) {
            stack = new Stack<InnerProfiler>();
            threadMap.set(stack);
        }

        InnerProfiler ip = new InnerProfiler(stArray[pos],msg);//减去三次拿到Profiler外的真正的第一层调用情况不可能小3
        stack.push(ip);
    }

    /**
     * 需要和release方式配合使用
     *
     * @author liukunyang
     * @date 2013-9-22
     */
    public static void enter() {
        doEnter("");
    }

    public static void enter(String msg){
        doEnter(msg);
    }


    /**
     * 需要和enter()方式配合使用
     *
     * @author liukunyang
     * @date 2013-9-22
     */
    public static void release() {

        Stack<InnerProfiler> stack = threadMap.get();

        InnerProfiler ip = stack.pop();
        ip.release();
        // ip.print();
        //将当前弹出的对象指向当前栈的栈顶，
        //目的是保留调用信息  形成链表
        if( stack.size()>0 ){
            InnerProfiler next = stack.elementAt(stack.size()-1);
            ip.next = next;
        }

        //开始计算  excuteList中的存放顺序
        List<InnerProfiler> excuteList = excuteResultMap.get();
        if( excuteList == null ){
            excuteList = new ArrayList<InnerProfiler>();
        }

        excuteList.add(ip);
        excuteResultMap.set(excuteList);

        if( stack.size() == 0 ){
            printTimeRate(excuteList);
        }
    }

    /**
     * 第二版特性，增加时间占用比例，因为堆栈的关系，
     * list里面存放的顺序并不一定正确
     * 比如 A
     *      -->B
     *      -->C
     * 如果直接使用list的话，顺序是 B-->C-->A (释放顺序)
     * 但是真正的应该是 B-->A C-->A
     * 这才能真正计算出B在A中所占的比例， C在A中所占的比例
     *
     * TODO 不使用list 而使用双向链表可否实现？
     * @param profilerList
     */
    private static void printTimeRate(List<InnerProfiler> profilerList){
        InnerProfiler ip=null;
        for( int i = 0 ; i<profilerList.size() ; i ++){
            ip = profilerList.get(i);
            InnerProfiler next = ip.next;
            if( null == next ){
                ip.rateInfo ="";
            }else{
                //计算比例
                long outTime = next.endTime - next.startTime;
                long innerTime = ip.endTime - ip.startTime;
                double rate = new  Double(innerTime)/ new Double(outTime);
                ip.rateInfo = "占用上一层："+ next.msg + ",耗时比例："+ String.format("%10.1f%%",  rate * 100 ).trim();
            }
            ip.print();

        }
    }

    /**
     * 调用层次对应实体类
     *
     * @author liukunyang
     * @version V1.0
     * @Package com.jd.jshop
     * @date 2013-9-22 下午02:00:16
     */
    static class InnerProfiler {

        private String methodName;

        private Long startTime;

        private Long endTime;

        private String className;

        private String msg;

        private InnerProfiler next;

        /**
         * 当前埋点实体 所占上层比例
         */
        private String rateInfo;





        public InnerProfiler(StackTraceElement st , String msg) {
            if (st == null) {
                throw new RuntimeException("StackTraceElement is empty");
            }
            this.methodName = st.getMethodName();
            this.className = st.getClassName();
            this.startTime = System.currentTimeMillis();
            this.msg=msg;
        }

        public void release() {
            this.endTime = System.currentTimeMillis();
        }

        public void print() {
            StringBuffer sb = new StringBuffer();
            sb.append(this.className);
            sb.append(".");
            sb.append(this.methodName);
            sb.append(",");
            sb.append(this.msg);
            sb.append("  take : ");
            sb.append( (this.endTime - this.startTime) + "ms" );
            sb.append("   ");
            sb.append(rateInfo);
            //TODO info 级别才打印，思考有没有更好的方式去展现。
            //比如拿到日志级别在确定怎么打印？
            log.info(sb);
        }

    }

}
