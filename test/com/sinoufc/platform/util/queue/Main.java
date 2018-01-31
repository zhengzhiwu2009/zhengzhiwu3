package com.sinoufc.platform.util.queue;

import java.util.UUID;

import com.sinoufc.platform.util.queue.Task.OnFinishListen;
import com.sinoufc.platform.util.queue.Task.OnStartListen;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Task.setThreadMaxNum(50);
		for (int i = 0; i < 15000; i++) {
			new Task() {

				@Override
				public String obtainData(Task task, Object parameter)
						throws Exception {
					// TODO Auto-generated method stub
					Thread.sleep(100);
					return "success";
				}

			}
			/*.setOnFinishListen(new OnFinishListen() {

				@Override
				public void onFinish(Task task, Object data) {
					// TODO Auto-generated method stub
					System.err.println("任务编号"+task.taskID+"任务完成");
				}
			})
			.setOnStartListen(new OnStartListen() {
				@Override
				public void onStart(Task task){
					System.err.println("任务编号"+task.taskID+"任务开始！");
				}
			})*/.setTaskID(UUID.randomUUID().toString())
			.start();
		}
	}

}
