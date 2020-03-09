package cn.ac.ict.modules.ws.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/** 
 * @ClassName: DuetimeListener
 * @description: 
 * @author: LiGuoqiang
 * @Date: 2020年2月20日 下午3:57:12
 */
public class DuetimeListener implements ServletContextListener{
	private Timer timer = null; 
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
	}
 
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		
		System.out.println("定时器启动");
		
		try {
			timer = new Timer(true);
			timer.scheduleAtFixedRate(new TestTimeTask(), 0, 1000 * 30);  //隔30秒(周期性)扫描执行方法，
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}
 

