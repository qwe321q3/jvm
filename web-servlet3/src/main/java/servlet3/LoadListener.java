package servlet3;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author tianshuo
 */
//@WebListener
public class LoadListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("---------------");
		System.out.println(sce.getServletContext().getServerInfo());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
