package com.whaty.platform.entity.web.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class GridSearchParamQueue implements Serializable {

	private final Integer QUEUE_CAPACITY = 3;
	private Queue<ParamVO> cache = new LinkedList<ParamVO>();
	
	public void saveParamInQueue(String uri,Map<String,Object> params){
		ParamVO vo = getParamInQueue(uri);
		if(vo == null){
			if(cache.size() < QUEUE_CAPACITY){
				cache.add(new ParamVO(uri,params));
			}else{
				cache.poll();
				cache.add(new ParamVO(uri,params));
			}
		}else{
			cache.remove(vo);
			vo.setParams(params);
			cache.add(vo);
		}
	}
	
	public ParamVO getParamInQueue(String uri){
		Iterator<ParamVO> it = cache.iterator();
		while(it.hasNext()){
			ParamVO vo = it.next();
			if(uri.equals(vo.getUri())){
				return vo;
			}
		}
		return null;
	}
	
	
	public Map<String,Object> getParamMapInQueue(String uri){
		Iterator<ParamVO> it = cache.iterator();
		while(it.hasNext()){
			ParamVO vo = it.next();
			if(uri.equals(vo.getUri())){
				return vo.getParams();
			}
		}
		return null;
	}
	
}
