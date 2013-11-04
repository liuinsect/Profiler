package com.liusoft.tools.test;

import com.liusoft.tools.profiler.Profiler;
import com.liusoft.tools.profiler.ProfilerCallBack;
import org.junit.Test;


/**
 * Hello world!
 *
 */
public class App 
{
	public void call() throws InterruptedException{
        Thread.sleep(200);
		new App1().call();
	}
	
	@Test
	public void test_1(){
		Profiler.enter();
        try {
			new App().call();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Profiler.release();
	}
	
	@Test
	public void test_2(){
		Profiler.enter( new ProfilerCallBack(){

			@Override
			public Object excute() {
				try {
					new App().call();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		});
	}
	
	
    public static void main( String[] args ) throws InterruptedException
    {
    	Profiler.enter( new ProfilerCallBack() {
			
			@Override
			public Object excute() {
				try {
					new App().call();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
    }

    public static class App1 {

        public void call() throws InterruptedException{
            Profiler.enter(new ProfilerCallBack() {

                @Override
                public Object excute() {
                    try {
                        new App2().call();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }

    }

    public static class App2 {

        public void call() throws InterruptedException{
            Thread.sleep(1000);
        }

    }
}
