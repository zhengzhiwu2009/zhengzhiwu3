package com.whaty.platform.entity.service.imp.studentStatus;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.framework.cache.aopcache.annotation.Cacheable;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.StuPeBulletinViewService;
import com.whaty.platform.entity.util.Page;

public class StuPeBulletinViewServiceImp implements StuPeBulletinViewService {

	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	@Cacheable(extension="[0]",tTLSeconds=30)
	public PeBzzStudent getLoginStudent(String userId) {
//		System.out.println("getLoginStudent ===================== ");
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.eq("ssoUser.id", userId));
		List<PeBzzStudent> student = null;
			student = this.generalDao.getList(dc);
		if (student != null && !student.isEmpty()) {
			//this.setPeBzzStudent(student.get(0));
			return (PeBzzStudent) student.get(0);
			
		}
		return null;
	}

	@Override
	@Cacheable(extension="[0]",tTLSeconds=30)
	public Page firstBulletinInfo(String loginId) {
		String sql = 
			"select pb.id as id,\n" +
			"       pb.title as title,\n" + 
			"       pb.publish_date as publishDate,\n" + 
			"       pe.true_name,\n" +
			"		ec.name\n" +
			"  from pe_bulletin pb, sso_user so, pe_manager pe, enum_const ec \n" + 
			" where pe.id = pb.fk_manager_id\n" + 
			"   and so.login_id = '"+loginId+"'\n" + 
			"   and pb.scope_string like '%students%'\n" +
			"	and ec.id = pb.flag_istop \n" +
			"	order by ec.name desc \n";
		Page page = this.generalDao.getByPageSQL(sql, 10, 0);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	@Override
//	@Cacheable(extension="[0]",tTLSeconds=30) 暂时注掉
	public Page firstBulletinInfoByPage(String loginId , int pageSize, int startIndex) {
		//1查出当前登陆学员所属一级机构的机构号。
		String sqlCode = "(select code from pe_enterprise where id=(select t1.id from(\n" +
			    "     select (case when pe.fk_parent_id is null then pe.id else pe.fk_parent_id end)id \n" +
			    "     from pe_enterprise pe, pe_bzz_student stu where pe.id=stu.fk_enterprise_id and stu.reg_no='"+loginId+"')t1))";
		List list = this.generalDao.getBySQL(sqlCode);
		//2查出机构
		String code = list.get(0).toString();
			String sql = 
				"select pb.id as id,\n" +
				"       pb.title as title,\n" + 
				"       pb.publish_date as publishDate,\n" + 
				"       pe.true_name,\n" +
				"		ec.name\n" +
				"  from pe_bulletin pb, pe_manager pe, enum_const ec \n" + 
				" where pe.id = pb.fk_manager_id\n" + 
				"	and ec.id = pb.flag_isvalid\n" +
			    "       and ec.code='1'\n" +
			    "      and pb.scope_string like '%student%'\n" +
			    "      and pb.scope_string like '%"+code+"%'\n" +
			    "     order by pb.flag_istop desc,pb.publish_date desc, ec.name desc ";
			Page page = this.generalDao.getByPageSQL(sql, pageSize, startIndex);
			return page;
	}

	@Override
	@Cacheable(extension="[0]",tTLSeconds=30)
	public List serviceAllPebulletins(String loginId) {
		System.out.println("serviceAllPebulletins------------------------------");
		
		
		String sql = 
			"select pb.id as id,\n" +
			"       pb.title as title,\n" + 
			"       pb.publish_date as publishDate,\n" + 
			"       pe.true_name,\n" + 
			"		ec.name\n" +
			"  from pe_bulletin pb, sso_user so, pe_manager pe, enum_const ec\n" + 
			" where pe.id = pb.fk_manager_id\n" + 
			"   and so.login_id = '"+loginId+"'\n" + 
			"   and pb.scope_string like '%students%'"+
			"	and ec.id = pb.flag_istop \n"+
			"	order by ec.name desc \n";
	
			List list = this.generalDao.getBySQL(sql);
			return list;
	}

	@Override
	@Cacheable(extension="[0]",tTLSeconds=30)
	public List initAllPebulletins(String loginId) throws EntityException {
		String sqlCount = 
			"select count(*) from (select pb.id as id,\n" +
			"       pb.title as title,\n" + 
			"       pb.publish_date as publishDate,\n" + 
			"       pe.true_name,\n" + 
			"		ec.name\n" +
			"  from pe_bulletin pb, sso_user so, pe_manager pe, enum_const ec\n" + 
			" where pe.id = pb.fk_manager_id\n" + 
			"   and so.login_id = '"+loginId+"'\n" + 
			"   and pb.scope_string like '%students%'"+
			"	and ec.id = pb.flag_istop \n"+
			"	order by ec.name desc \n)";
		List countList = this.generalDao.getBySQL(sqlCount);
		int count = 0;
		if(countList != null) {
			if(countList.size()>0) {
				String strCount = (countList.get(0).toString()==null || countList.get(0).toString().equals("")) ? "0" : countList.get(0).toString();
				count = Integer.parseInt(strCount);
			}
		}
		
		String sql = 
			"select pb.id as id,\n" +
			"       pb.title as title,\n" + 
			"       pb.publish_date as publishDate,\n" + 
			"       pe.true_name,\n" +
			"		ec.name"+
			"  from pe_bulletin pb, sso_user so, pe_manager pe, enum_const ec \n" + 
			" where pe.id = pb.fk_manager_id\n" + 
			"   and so.login_id = '"+loginId+"'\n" + 
			"   and pb.scope_string like '%students%'"+
			"	and ec.id = pb.flag_istop \n" +
			"	order by ec.name desc \n";

		Page page = this.generalDao.getByPageSQL(sql, count, 0);
		List list = page.getItems();
		return list;
	}
}
