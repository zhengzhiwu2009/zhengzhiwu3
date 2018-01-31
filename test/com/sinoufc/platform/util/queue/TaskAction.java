package com.sinoufc.platform.util.queue;

public interface TaskAction<P,R>{
	public  R obtainData(Task<P,R> task, P parameter)throws Exception;		
}