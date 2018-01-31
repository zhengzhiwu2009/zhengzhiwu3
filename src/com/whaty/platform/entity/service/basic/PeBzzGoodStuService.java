package com.whaty.platform.entity.service.basic;

import java.util.List;


import com.whaty.platform.entity.bean.PeBzzGoodStu;

public interface PeBzzGoodStuService {
	
	public PeBzzGoodStu Save(PeBzzGoodStu goodStu);

	public boolean del(PeBzzGoodStu goodStu);
	
	public int update(List ids, String propety, String value);
	
	
}
