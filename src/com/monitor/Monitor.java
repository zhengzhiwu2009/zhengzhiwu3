package com.monitor;

import javax.jws.WebService;

@WebService
public interface Monitor {
	String loginCMSMonitor(); 
	String loginHTMonitor(); 
}
