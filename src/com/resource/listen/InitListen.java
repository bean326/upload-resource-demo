package com.resource.listen;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.resource.config.InitConfig;

/**
 * @author FXW
 * 2015年11月3日
 */
public class InitListen implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		InitConfig.init();
		
	}

}
