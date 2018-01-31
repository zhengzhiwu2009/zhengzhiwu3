package com.whaty.platform.entity.service.prepaidAccount;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

public interface PrepaidAccountService {

	/**
	 * 用于查看学员预付费分配/剥离金额
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param eleList
	 * @return
	 * @author cailei
	 */

	public Page viewStudentPrepaidAccountAdd(String recordId, String cardNo,int pageSize, int startIndex) throws EntityException;
	
	/**
	 * 用于查看学员预付费分配/剥离金额,去掉分页功能
	 * @param recordId
	 * @param cardNo
	 * @return
	 * @throws EntityException
	 */
	public List viewStudentPrepaidAccountAdd(String recordId, String cardNo) throws EntityException;
	
	/**
	 * 用于查看二级集体预付费分配/剥离金额
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param eleList
	 * @return
	 * @author cailei
	 */
	public Page viewEnterprisePrepaidAccountAdd(String recordId, String enterpriseName,int pageSize, int startIndex) throws EntityException;
	
	
	/**
	 * 用于查看二级集体预付费分配/剥离金额
	 * @param recordId
	 * @param enterpriseName
	 * @return
	 * @throws EntityException
	 */
	public List viewEnterprisePrepaidAccountAdd(String recordId, String enterpriseName) throws EntityException;
		
	/**
	 * 统计学员预付费分配剥离信息
	 * @param stuId
	 * @param coursename
	 * @return
	 * @throws EntityException
	 */
	public List getAmount(String recordId) throws EntityException;
	
	/**
	 * 统计集体预付费分配剥离信息
	 * @param stuId
	 * @param coursename
	 * @return
	 * @throws EntityException
	 */
	public List getEnterpriseAmount(String recordId) throws EntityException;


}
