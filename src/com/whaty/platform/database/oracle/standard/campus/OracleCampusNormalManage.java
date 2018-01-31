package com.whaty.platform.database.oracle.standard.campus;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusNormalManage;
import com.whaty.platform.campus.base.AssociationMember;
import com.whaty.platform.campus.base.ClassMember;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.database.oracle.standard.campus.base.OracleAssociation;
import com.whaty.platform.database.oracle.standard.campus.base.OracleAssociationList;
import com.whaty.platform.database.oracle.standard.campus.base.OracleAssociationMember;
import com.whaty.platform.database.oracle.standard.campus.base.OracleAssociationMemberList;
import com.whaty.platform.database.oracle.standard.campus.base.OracleClass;
import com.whaty.platform.database.oracle.standard.campus.base.OracleClassList;
import com.whaty.platform.database.oracle.standard.campus.base.OracleClassMember;
import com.whaty.platform.database.oracle.standard.campus.base.OracleClassMemberList;
import com.whaty.platform.database.oracle.standard.campus.user.OracleCampusManagerPriv;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleCampusNormalManage extends CampusNormalManage {

	StrManage strManage = StrManageFactory.creat();

	public OracleCampusNormalManage() {
		super();
		this.setPriv(new OracleCampusManagerPriv());
	}

	public OracleCampusNormalManage(String id) {
		super();
	}

	public OracleCampusNormalManage(CampusManagerPriv priv) {
		this.setPriv(priv);
	}

	public int addClass(HttpServletRequest request, HttpServletResponse response)
			throws PlatformException {
		OracleClass cla = new OracleClass();
		try {
			cla.setTitle(strManage.fixNull(request.getParameter("title")));
			cla.setStatus(strManage.fixNull(request.getParameter("status")));
			cla.setNote(strManage.fixNull(request.getParameter("note")));
			cla.setForumId(strManage.fixNull(request.getParameter("forum_id")));
			cla.setManagerId(this.getPriv().getManagerId());

		} catch (WhatyUtilException e) {
			throw new PlatformException("addClass error!(" + e.toString() + ")");
		}
		int sub = cla.add();
		return sub;
	}

	@Override
	public com.whaty.platform.campus.base.Class getClass(
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException {
		try {
			return new OracleClass(strManage
					.fixNull(request.getParameter("id")));
		} catch (Exception e) {
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public com.whaty.platform.campus.base.Class getClass(String id)
			throws PlatformException {
		return new OracleClass(id);
	}

	public List getClassList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleClassList classList = new OracleClassList();
		try {
			if (!strManage.fixNull(request.getParameter("title")).equals(""))
				searchProperty.add(new SearchProperty("title", request
						.getParameter("title"), "like"));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return classList.getClasses(page, searchProperty, orderProperty);
	}

	public int getClassNum(HttpServletRequest request) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleClassList classList = new OracleClassList();
		try {
			if (!strManage.fixNull(request.getParameter("title")).equals(""))
				searchProperty.add(new SearchProperty("title", request
						.getParameter("title"), "like"));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return classList.getClassesNum(searchProperty);
	}

	public ClassMember getClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		try {
			return new OracleClassMember(strManage.fixNull(request
					.getParameter("id")));
		} catch (Exception e) {
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public ClassMember getClassMember(String id) throws PlatformException {
		try {
			return new OracleClassMember(id);
		} catch (Exception e) {
			throw new PlatformException(e.getMessage());
		}
	}

	public List getClassMemberList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleClassMemberList classList = new OracleClassMemberList();
		try {
			if (!strManage.fixNull(request.getParameter("name")).equals(""))
				searchProperty.add(new SearchProperty("name", request
						.getParameter("name"), "like"));
			if (!strManage.fixNull(request.getParameter("class_id")).equals(""))
				searchProperty.add(new SearchProperty("class_id", request
						.getParameter("class_id"), "="));
			if (!strManage.fixNull(request.getParameter("member_type")).equals(
					""))
				searchProperty.add(new SearchProperty("member_type", request
						.getParameter("member_type"), "="));
			if (!strManage.fixNull(request.getParameter("link_id")).equals(""))
				searchProperty.add(new SearchProperty("link_id", request
						.getParameter("link_id"), "="));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return classList.getClassMembers(page, searchProperty, orderProperty);
	}

	@Override
	public int getClassMemberNum(HttpServletRequest request)
			throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleClassMemberList classList = new OracleClassMemberList();
		try {
			if (!strManage.fixNull(request.getParameter("name")).equals(""))
				searchProperty.add(new SearchProperty("name", request
						.getParameter("name"), "like"));
			if (!strManage.fixNull(request.getParameter("class_id")).equals(""))
				searchProperty.add(new SearchProperty("class_id", request
						.getParameter("class_id"), "="));
			if (!strManage.fixNull(request.getParameter("member_type")).equals(
					""))
				searchProperty.add(new SearchProperty("member_type", request
						.getParameter("member_type"), "="));
			if (!strManage.fixNull(request.getParameter("link_id")).equals(""))
				searchProperty.add(new SearchProperty("link_id", request
						.getParameter("link_id"), "="));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return classList.getClassMembersNum(searchProperty);
	}

	public int addAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		OracleAssociation cla = new OracleAssociation();
		try {
			cla.setTitle(strManage.fixNull(request.getParameter("title")));
			cla.setStatus(strManage.fixNull(request.getParameter("status")));
			cla.setNote(strManage.fixNull(request.getParameter("note")));
			cla.setForumId(strManage.fixNull(request.getParameter("forum_id")));
			cla.setManagerId(this.getPriv().getManagerId());

		} catch (WhatyUtilException e) {
			throw new PlatformException("addAssociation error!(" + e.toString()
					+ ")");
		}
		int sub = cla.add();
		return sub;
	}

	@Override
	public com.whaty.platform.campus.base.Association getAssociation(
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException {
		try {
			return new OracleAssociation(strManage.fixNull(request
					.getParameter("id")));
		} catch (Exception e) {
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public com.whaty.platform.campus.base.Association getAssociation(String id)
			throws PlatformException {
		return new OracleAssociation(id);
	}

	public List getAssociationList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleAssociationList associationList = new OracleAssociationList();
		try {
			if (!(strManage.fixNull(request.getParameter("title")).equals("")))
				searchProperty.add(new SearchProperty("title", request
						.getParameter("title"), "like"));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return associationList.getAssociationes(page, searchProperty,
				orderProperty);
	}

	public int getAssociationNum(HttpServletRequest request)
			throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleAssociationList associationList = new OracleAssociationList();
		try {
			if (!strManage.fixNull(request.getParameter("title")).equals(""))
				searchProperty.add(new SearchProperty("title", request
						.getParameter("title"), "like"));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return associationList.getAssociationesNum(searchProperty);
	}

	public AssociationMember getAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		try {
			return new OracleAssociationMember(strManage.fixNull(request
					.getParameter("id")));
		} catch (Exception e) {
			throw new PlatformException(e.getMessage());
		}
	}

	@Override
	public AssociationMember getAssociationMember(String id)
			throws PlatformException {
		try {
			return new OracleAssociationMember(id);
		} catch (Exception e) {
			throw new PlatformException(e.getMessage());
		}
	}

	public List getAssociationMemberList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleAssociationMemberList associationList = new OracleAssociationMemberList();
		try {
			if (!strManage.fixNull(request.getParameter("name")).equals(""))
				searchProperty.add(new SearchProperty("name", request
						.getParameter("name"), "like"));
			if (!strManage.fixNull(request.getParameter("association_id"))
					.equals(""))
				searchProperty.add(new SearchProperty("association_id", request
						.getParameter("association_id"), "="));
			if (!strManage.fixNull(request.getParameter("member_type")).equals(
					""))
				searchProperty.add(new SearchProperty("member_type", request
						.getParameter("member_type"), "="));
			if (!strManage.fixNull(request.getParameter("link_id")).equals(""))
				searchProperty.add(new SearchProperty("link_id", request
						.getParameter("link_id"), "="));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return associationList.getAssociationMembers(page, searchProperty,
				orderProperty);
	}

	@Override
	public int getAssociationMemberNum(HttpServletRequest request)
			throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleAssociationMemberList associationList = new OracleAssociationMemberList();
		try {
			if (!strManage.fixNull(request.getParameter("name")).equals(""))
				searchProperty.add(new SearchProperty("name", request
						.getParameter("name"), "like"));
			if (!strManage.fixNull(request.getParameter("association_id"))
					.equals(""))
				searchProperty.add(new SearchProperty("association_id", request
						.getParameter("association_id"), "="));
			if (!strManage.fixNull(request.getParameter("member_type")).equals(
					""))
				searchProperty.add(new SearchProperty("member_type", request
						.getParameter("member_type"), "="));
			if (!strManage.fixNull(request.getParameter("link_id")).equals(""))
				searchProperty.add(new SearchProperty("link_id", request
						.getParameter("link_id"), "="));
		} catch (WhatyUtilException e) {
			throw new PlatformException();
		}
		return associationList.getAssociationMembersNum(searchProperty);
	}

	public List getCreateNewAssociationList() throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleAssociationList associationList = new OracleAssociationList();
		searchProperty.add(new SearchProperty("status", "1", "="));
		orderProperty.add(new OrderProperty("apply_date", OrderProperty.DESC));
		return associationList.getAssociationes(null, searchProperty,
				orderProperty);
	}

	public List getTopAssociationList(Page page) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		OracleAssociationList associationList = new OracleAssociationList();
		searchProperty.add(new SearchProperty("status", "1", "="));
		return associationList.getAssociationes(page, searchProperty,
				orderProperty);
	}

	public int getTopAssociationListNum() throws PlatformException {
		List searchProperty = new ArrayList();
		OracleAssociationList associationList = new OracleAssociationList();
		searchProperty.add(new SearchProperty("status", "1", "="));
		
		return associationList.getAssociationesNum(searchProperty);
	}
	public List getClassForumList(Page page, String classId, boolean isNew,
			int displayNum,String keyword) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		try {
			if (!strManage.fixNull(classId).equals(""))
				searchProperty.add(new SearchProperty("id",classId, "="));
			if (!strManage.fixNull(keyword).equals(""))
				searchProperty.add(new SearchProperty("title",keyword, "like"));
			if(displayNum != -1)
				searchProperty.add(new SearchProperty("rownum",String.valueOf(displayNum), "="));
			
			orderProperty.add(new OrderProperty("title",OrderProperty.DESC));
			if(isNew)
				orderProperty.add(new OrderProperty("create_date",OrderProperty.DESC));
			
		} catch (WhatyUtilException e) {
			
		}
		return new OracleClassList().getClassForums(page,searchProperty,orderProperty);
	}
	
	public int getClassForumNum(String classId,String keyword) throws PlatformException {
		List searchProperty = new ArrayList();
		try {
			if (!strManage.fixNull(classId).equals(""))
				searchProperty.add(new SearchProperty("id",classId, "="));
			if (!strManage.fixNull(keyword).equals(""))
				searchProperty.add(new SearchProperty("title",keyword, "like"));
			
		} catch (WhatyUtilException e) {
			
		}
		return new OracleClassList().getClassForumesNum(searchProperty);
	}

	@Override
	public boolean isCurrentClassMember(String classId, String memberId) throws PlatformException {
		boolean isTrue = false;
		OracleClassMemberList memberlist = new OracleClassMemberList();
		List list = (List)memberlist.getClassMembers(classId,null,memberId);
		if(list.size() > 0)
			isTrue = true;
		else
			isTrue = false;
		return isTrue;
	}
}

