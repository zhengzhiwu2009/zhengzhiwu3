package com.whaty.platform.entity.service.basic;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;

public interface PeEnterpriseBacthService {

	public int Bacthsave(File filetest) throws EntityException;

	public PeEnterprise save(PeEnterprise bean, String pcode);
	
	public boolean deletePe(List idList);
	
	public boolean setValivd(List idList);
	
	public boolean setInValivd(List idList);
	
	public boolean resetPassword(List idList);
	
}
