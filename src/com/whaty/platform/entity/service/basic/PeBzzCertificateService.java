package com.whaty.platform.entity.service.basic;


import java.io.File;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;

public interface PeBzzCertificateService {
	
	public int Bacthsave(File file)throws EntityException, MessageException;
	
}