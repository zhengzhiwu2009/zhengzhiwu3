package com.whaty.platform.entity.service.imp.prepaidAccount;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.whaty.framework.cache.aopcache.annotation.Cacheable;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.prepaidAccount.PrepaidAccountService;
import com.whaty.platform.entity.service.studentStatas.StudentWorkspaceService;
import com.whaty.platform.entity.util.Page;

public class PrepaidAccountServiceImp implements PrepaidAccountService {
	private GeneralDao generalDao;
	private EnumConstDAO enumConstDao;

	/**
	 * 用于查看学员预付费分配/剥离金额
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param eleList
	 * @return
	 * @author cailei
	 */
	@Override
	public Page viewStudentPrepaidAccountAdd(String recordId, String cardNo,int pageSize, int startIndex) throws EntityException{
		String sql ="select ar.id ,to_char(assign_date,'yyyy-mm-dd hh24:mi:ss'), pbs.true_name, ars.class_num, pbs.card_no "+
		" from assign_record ar  left join ASSIGN_RECORD_STUDENT ars   "+
		" on ar.id = ars.fk_record_id  left join pe_bzz_student pbs   "+
		" on ars.fk_student_id = pbs.id where ar.id = '"+recordId+"'  ";
		if ( null!= cardNo) {
			if (!"".equals(cardNo)) {
				sql += "and  pbs.card_no ='" + cardNo + "'";
			}
		}
		Page page = null;
		page = this.getGeneralDao().getByPageSQL(sql, pageSize, startIndex);
		return page;
			
	}
	
	
	@Override
	public List viewStudentPrepaidAccountAdd(String recordId, String cardNo) throws EntityException{
		String sql ="select ar.id ,to_char(assign_date,'yyyy-mm-dd hh24:mi:ss'), pbs.true_name, ars.class_num, pbs.card_no "+
		" from assign_record ar  left join ASSIGN_RECORD_STUDENT ars   "+
		" on ar.id = ars.fk_record_id  left join pe_bzz_student pbs   "+
		" on ars.fk_student_id = pbs.id where ar.id = '"+recordId+"'  ";
		if ( null!= cardNo) {
			if (!"".equals(cardNo)) {
				sql += "and  pbs.card_no ='" + cardNo + "'";
			}
		}
		List list = null;
		list = this.getGeneralDao().getBySQL(sql);
		return list;
			
	}
	
	/**
	 * 用于查看学员预付费分配/剥离金额
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param eleList
	 * @return
	 * @author cailei
	 */
	@Override
	public Page viewEnterprisePrepaidAccountAdd(String recordId, String enterpriseName,int pageSize, int startIndex) throws EntityException{
		String sql =" select ar.id, to_char(assign_date,'yyyy-mm-dd hh24:mi:ss'), pe.name, ars.class_num   from assign_record ar   "+
		"left join ASSIGN_RECORD_STUDENT ars     on ar.id = ars.fk_record_id  "+
		" left join pe_enterprise_manager pem     on ars.FK_ENTERPRISE_MANAGER_ID = pem.id "+
		"    left join pe_enterprise pe     on pem.fk_enterprise_id=pe.id  where ar.id ='"+recordId+"'";
		if ( null!= enterpriseName) {
			if (!"".equals(enterpriseName)) {
				sql += "and  pe.name like '%" + enterpriseName + "%'";
			}
		}
		Page page = null;
		page = this.getGeneralDao().getByPageSQL(sql, pageSize, startIndex);
		return page;
			
	}
	
	/**
	 * 用于查看二级集体预付费分配/剥离金额
	 */
	@Override
	public List viewEnterprisePrepaidAccountAdd(String recordId, String enterpriseName) throws EntityException{
		String sql =" select ar.id, to_char(assign_date,'yyyy-mm-dd hh24:mi:ss'), pe.name, ars.class_num   from assign_record ar   "+
		"left join ASSIGN_RECORD_STUDENT ars     on ar.id = ars.fk_record_id  "+
		" left join pe_enterprise_manager pem     on ars.FK_ENTERPRISE_MANAGER_ID = pem.id "+
		"    left join pe_enterprise pe     on pem.fk_enterprise_id=pe.id  where ar.id ='"+recordId+"'";
		if ( null!= enterpriseName) {
			if (!"".equals(enterpriseName)) {
				sql += "and  pe.name like '%" + enterpriseName + "%'";
			}
		}
		List page = null;
		page = this.getGeneralDao().getBySQL(sql);
		return page;
			
	}
	
	

	/**
	 * 统计学员预付费分配剥离信息
	 * @param stuId
	 * @param coursename
	 * @return
	 * @throws EntityException
	 */
	public List getAmount(String recordId) throws EntityException{
		String sql = "select count(FK_STUDENT_ID),sum(CLASS_NUM)  from assign_record ar  "+
		"left join ASSIGN_RECORD_STUDENT ars    on ar.id = ars.fk_record_id "+
		" left join pe_bzz_student pbs    on ars.fk_student_id = pbs.id where ar.id='"+recordId+"'";
		return this.generalDao.getBySQL(sql);
	};
	
	/**
	 * 统计集体预付费分配剥离信息
	 * @param stuId
	 * @param coursename
	 * @return
	 * @throws EntityException
	 */
	public List getEnterpriseAmount(String recordId) throws EntityException{
		String sql = "select count(FK_ENTERPRISE_MANAGER_ID), sum(CLASS_NUM)  "+
		"from assign_record ar  left join ASSIGN_RECORD_STUDENT ars    "+
		"on ar.id = ars.fk_record_id where ar.id ='"+recordId+"'";
		return this.generalDao.getBySQL(sql);
	};
	

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public EnumConstDAO getEnumConstDao() {
		return enumConstDao;
	}

	public void setEnumConstDao(EnumConstDAO enumConstDao) {
		this.enumConstDao = enumConstDao;
	};


	
}
