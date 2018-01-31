package com.whaty.platform.entity.service.basic;

import java.util.List;


import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzGoodMag;
import com.whaty.platform.entity.bean.PeBzzGoodStu;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;

public interface PeBzzGoodMagService {
	
	public PeBzzGoodMag Save(PeBzzGoodMag goodMag);

	public boolean del(PeBzzGoodMag goodMag);
	
	public int update(List ids, String propety, String value);
	
	public void updateFlaggoodMag(PeEnterpriseManager peEnterpriseManager, EnumConst enumConst);
	
}
