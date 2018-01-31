package com.whaty.platform.database.oracle.standard.campus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusManage;
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
import com.whaty.platform.interaction.InteractionFactory;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public class OracleCampusManage extends CampusManage {
	StrManage strManage = StrManageFactory.creat();

	public OracleCampusManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OracleCampusManage(CampusManagerPriv priv) {
		this.setPriv(priv);
		// TODO Auto-generated constructor stub
	}

	public int addClass(HttpServletRequest request, HttpServletResponse response)
			throws PlatformException {
		if (this.getPriv().applyClass == 1) {
			int suc = 0;
			OracleClass cla = new OracleClass();
			try {
				cla.setTitle(strManage.fixNull(request.getParameter("title")));
				cla
						.setStatus(strManage.fixNull(request
								.getParameter("status")));
				cla.setNote(strManage.fixNull(request.getParameter("note")));
				cla.setForumId(strManage.fixNull(request
						.getParameter("forum_id")));
				cla.setManagerId(strManage.fixNull(request
						.getParameter("manager_id")));
				cla.setCreaterType(strManage.fixNull(request
						.getParameter("creater_type")));
				suc = cla.add();
				if (suc > 0) {
					OracleClassMember member = new OracleClassMember(strManage
							.fixNull(request.getParameter("manager_id")),
							strManage.fixNull(request
									.getParameter("creater_type")), String
									.valueOf(suc));
					member.setStatus("0");
					member.setRole("0");
					cla.setId(String.valueOf(suc));
					cla.setManagerName(member.getName());
					cla.updateManagerName();
					suc = member.add();
				}
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				throw new PlatformException("addClass error!(" + e.toString()
						+ ")");
			}

			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addClass</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û������༶��Ȩ��");
		}
	}

	@Override
	public com.whaty.platform.campus.base.Class getClass(
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException {
		if (this.getPriv().getClass == 1) {
			try {
				return new OracleClass(strManage.fixNull(request
						.getParameter("id")));
			} catch (Exception e) {
				throw new PlatformException(e.getMessage());
			}
		} else {
			throw new PlatformException("��û�в�ѯ�༶��Ȩ��");
		}
	}

	@Override
	public com.whaty.platform.campus.base.Class getClass(String id)
			throws PlatformException {
		if (this.getPriv().getClass == 1) {
			return new OracleClass(id);
		} else {
			throw new PlatformException("��û�в�ѯ�༶��Ȩ��");
		}
	}
	
	@Override
	public com.whaty.platform.campus.base.Class getClassByName(String name)
			throws PlatformException {
		if (this.getPriv().getClass == 1) {
			return new OracleClass(name);
		} else {
			throw new PlatformException("��û�в�ѯ�༶��Ȩ��");
		}
	}

	@Override
	public int delClass(String id) throws PlatformException {
		if (this.getPriv().deleteClass == 1) {
			OracleClass cla = new OracleClass();
			cla.setId(id);
			int suc = cla.delete();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$delClass</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$ɾ��༶</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ��༶��Ȩ��");
		}
	}

	@Override
	public List getClassList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().getClass == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			OracleClassList classList = new OracleClassList();
			try {
				if (!strManage.fixNull(request.getParameter("title"))
						.equals(""))
					searchProperty.add(new SearchProperty("title", request
							.getParameter("title"), "like"));
				if (!strManage.fixNull(request.getParameter("id")).equals(""))
					searchProperty.add(new SearchProperty("id", request
							.getParameter("id"), "="));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			return classList.getClasses(page, searchProperty, orderProperty);
		} else {
			throw new PlatformException("��û�в�ѯ�༶��Ȩ��");
		}
	}

	@Override
	public int confirmClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkClass == 1) {
			int suc = 0;
			OracleClass cla = new OracleClass();
			String classId = request.getParameter("id");
			String memberType = request.getParameter("creater_type");
			String linkId = request.getParameter("manager_id");
			String checker = request.getParameter("checker");
			cla.setId(classId);
			if (cla.confirm() > 0) {
				List list = new OracleClassMemberList().getClassMembers(
						classId, null, linkId);
				if (list.size() < 1) {
					OracleClassMember member = new OracleClassMember(linkId,
							memberType, classId);
					member.setStatus("1");
					member.setRole("1");
					suc = member.add();
				} else {
					OracleClassMember member = (OracleClassMember) list.get(0);
					suc = member.assignManager();
					suc += member.confirm();
				}

			}
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$confirmClass</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$��˰༶</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����˰༶��Ȩ��");
		}
	}

	@Override
	public int unConfirmClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkClass == 1) {
			OracleClass cla = new OracleClass();
			OracleClassMember member = null;
			String classId = request.getParameter("id");
			String memberType = request.getParameter("creater_type");
			cla.setId(classId);
			int suc = cla.unConfirm();
			/*
			 * if(cla.unConfirm() > 0) { List members = new
			 * OracleClassMemberList().getClassMembers(classId,memberType,null);
			 * for(int i=0;i<members.size();i++) { member =
			 * (OracleClassMember)members.get(i); suc += member.unConfirm(); }
			 * if(members.size()==0) suc = 1; }
			 */
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$unConfirmClass</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$���δͨ��</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����˰༶��Ȩ��");
		}
	}

	public int updateClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().updateClass == 1) {
			OracleClass cla = new OracleClass();
			try {
				cla.setId(strManage.fixNull(request.getParameter("id")));
				cla.setTitle(strManage.fixNull(request.getParameter("title")));
				cla.setNote(strManage.fixNull(request.getParameter("note")));
				cla
						.setStatus(strManage.fixNull(request
								.getParameter("status")));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			int suc = cla.update();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$updateClass</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$���°༶��Ϣ</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸İ༶��Ȩ��");
		}
	}

	@Override
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

	@Override
	public int addClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().applyClassMember == 1) {
			OracleClassMember member = null;
			try {
				String classId = strManage.fixNull(request
						.getParameter("class_id"));
				String memberType = strManage.fixNull(request
						.getParameter("member_type"));
				String linkId = strManage.fixNull(request
						.getParameter("link_id"));
				member = new OracleClassMember(linkId, memberType, classId);
			} catch (WhatyUtilException e) {
				throw new PlatformException("addClassMember error!("
						+ e.toString() + ")");
			}
			int sub = member.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addClassMember</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$��Ӱ༶��Ա</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("��û�м���༶��Ȩ��");
		}
	}

	@Override
	public ClassMember getClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().getClassMember == 1) {
			try {
				return new OracleClassMember(strManage.fixNull(request
						.getParameter("id")));
			} catch (Exception e) {
				throw new PlatformException(e.getMessage());
			}
		} else {
			throw new PlatformException("��û�в�ѯ�༶��Ա��Ȩ��");
		}
	}

	@Override
	public ClassMember getClassMember(String id) throws PlatformException {
		if (this.getPriv().getClassMember == 1) {
			try {
				return new OracleClassMember(id);
			} catch (Exception e) {
				throw new PlatformException(e.getMessage());
			}
		} else {
			throw new PlatformException("��û�в�ѯ�༶��Ա��Ȩ��");
		}
	}

	@Override
	public int delClassMember(String id) throws PlatformException {
		if (this.getPriv().deleteClassMember == 1) {
			OracleClassMember cla = new OracleClassMember();
			cla.setId(id);
			int suc = cla.delete();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$delClassMember</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$ɾ��༶��Ա</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ��༶��Ա��Ȩ��");
		}
	}

	@Override
	public List getClassMemberList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().getClassMember == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			OracleClassMemberList classList = new OracleClassMemberList();
			try {
				if (!strManage.fixNull(request.getParameter("name")).equals(""))
					searchProperty.add(new SearchProperty("name", request
							.getParameter("name"), "like"));
				if (!strManage.fixNull(request.getParameter("class_id"))
						.equals(""))
					searchProperty.add(new SearchProperty("class_id", request
							.getParameter("class_id"), "="));
				if (!strManage.fixNull(request.getParameter("member_type"))
						.equals(""))
					searchProperty.add(new SearchProperty("member_type",
							request.getParameter("member_type"), "="));
				if (!strManage.fixNull(request.getParameter("link_id")).equals(
						""))
					searchProperty.add(new SearchProperty("link_id", request
							.getParameter("link_id"), "="));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			return classList.getClassMembers(page, searchProperty,
					orderProperty);
		} else {
			throw new PlatformException("��û�в�ѯ�༶��Ա��Ȩ��");
		}
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

	@Override
	public int confirmClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkClassMember == 1) {
			OracleClassMember cla = new OracleClassMember();
			cla.setId(request.getParameter("id"));
			int suc = cla.confirm();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$confirmClassMember</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$�༶��Ա���ͨ��</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����˰༶��Ա��Ȩ��");
		}
	}

	@Override
	public int unConfirmClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkClassMember == 1) {
			OracleClassMember cla = new OracleClassMember();
			cla.setId(request.getParameter("id"));
			int suc = cla.unConfirm();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$unConfirmClassMember</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$unConfirmClassMember</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û����˰༶��Ա��Ȩ��");
		}
	}

	public int updateClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().updateClassMember == 1) {
			OracleClassMember cla = new OracleClassMember();
			try {
				cla.setId(strManage.fixNull(request.getParameter("id")));
				cla.setName(strManage.fixNull(request.getParameter("name")));
				cla
						.setStatus(strManage.fixNull(request
								.getParameter("status")));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			int suc = cla.update();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$updateClassMember</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$updateClassMember</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸İ༶��Ա��Ϣ��Ȩ��");
		}
	}

	public int addAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().applyAssociation == 1) {
			int suc = 0;
			OracleAssociation cla = new OracleAssociation();
			try {
				cla.setTitle(strManage.fixNull(request.getParameter("title")));
				cla
						.setStatus(strManage.fixNull(request
								.getParameter("status")));
				cla.setNote(strManage.fixNull(request.getParameter("note")));
				cla.setForumId(strManage.fixNull(request
						.getParameter("forum_id")));
				cla.setManagerId(strManage.fixNull(request
						.getParameter("manager_id")));
				cla.setCreaterType(strManage.fixNull(request
						.getParameter("creater_type")));
				suc = cla.add();
				if (suc > 0) {
					OracleAssociationMember member = new OracleAssociationMember(
							strManage.fixNull(request
									.getParameter("manager_id")), strManage
									.fixNull(request
											.getParameter("creater_type")),
							String.valueOf(suc));
					member.setStatus("0");
					member.setRole("0");
					cla.setId(String.valueOf(suc));
					cla.setManagerName(member.getName());
					cla.updateManagerName();
					
					suc = member.add();
				}
			} catch (WhatyUtilException e) {
				// TODO Auto-generated catch block
				throw new PlatformException("addAssociation error!("
						+ e.toString() + ")");
			}

			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addAssociation</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$addAssociation</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���������ŵ�Ȩ��");
		}
	}

	@Override
	public com.whaty.platform.campus.base.Association getAssociation(
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException {
		if (this.getPriv().getAssociation == 1) {
			try {
				return new OracleAssociation(strManage.fixNull(request
						.getParameter("id")));
			} catch (Exception e) {
				throw new PlatformException(e.getMessage());
			}
		} else {
			throw new PlatformException("��û�в�ѯ���ŵ�Ȩ��");
		}
	}

	@Override
	public com.whaty.platform.campus.base.Association getAssociation(String id)
			throws PlatformException {
		if (this.getPriv().getAssociation == 1) {
			return new OracleAssociation(id);
		} else {
			throw new PlatformException("��û�в�ѯ���ŵ�Ȩ��");
		}
	}

	@Override
	public int delAssociation(String id) throws PlatformException {
		if (this.getPriv().deleteAssociation == 1) {
			OracleAssociation cla = new OracleAssociation();
			cla.setId(id);
			int suc = cla.delete();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$delAssociation</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$delAssociation</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��ɾ�����ŵ�Ȩ��");
		}
	}

	@Override
	public List getAssociationList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().getAssociation == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			OracleAssociationList associationList = new OracleAssociationList();
			try {
				if (!strManage.fixNull(request.getParameter("title"))
						.equals(""))
					searchProperty.add(new SearchProperty("title", request
							.getParameter("title"), "like"));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			return associationList.getAssociationes(page, searchProperty,
					orderProperty);
		} else {
			throw new PlatformException("��û�в�ѯ���ŵ�Ȩ��");
		}
	}

	@Override
	public int confirmAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkAssociation == 1) {
			int suc = 0;
			OracleAssociation cla = new OracleAssociation();
			String associationId = request.getParameter("id");
			String memberType = request.getParameter("creater_type");
			String linkId = request.getParameter("manager_id");
			cla.setId(associationId);
			if (cla.confirm() > 0) {
				if (cla.confirm() > 0) {
					List list = new OracleAssociationMemberList()
							.getAssociationMembers(associationId, null, linkId);
					if (list.size() < 1) {
						OracleAssociationMember member = new OracleAssociationMember(
								linkId, memberType, associationId);
						member.setStatus("1");
						member.setRole("1");
						suc = member.add();
					} else {
						OracleAssociationMember member = (OracleAssociationMember) list
								.get(0);
						suc = member.assignManager();
						suc += member.confirm();
					}

				}
			}
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$confirmAssociation</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$confirmAssociation</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��������ŵ�Ȩ��");
		}
	}

	@Override
	public int unConfirmAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkAssociation == 1) {
			OracleAssociation cla = new OracleAssociation();
			OracleAssociationMember member = null;
			String associationId = request.getParameter("id");
			String memberType = request.getParameter("creater_type");
			cla.setId(associationId);
			int suc = cla.unConfirm();
			/*
			 * if(cla.unConfirm() > 0) { List members = new
			 * OracleAssociationMemberList().getAssociationMembers(associationId,memberType,null);
			 * for(int i=0;i<members.size();i++) { member =
			 * (OracleAssociationMember)members.get(i); suc +=
			 * member.unConfirm(); } if(members.size()==0) suc = 1; }
			 */
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$unConfirmAssociation</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$unConfirmAssociation</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û��������ŵ�Ȩ��");
		}
	}

	public int updateAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().updateAssociation == 1) {
			OracleAssociation cla = new OracleAssociation();
			try {
				cla.setId(strManage.fixNull(request.getParameter("id")));
				cla.setTitle(strManage.fixNull(request.getParameter("title")));
				cla.setNote(strManage.fixNull(request.getParameter("note")));
				cla
						.setStatus(strManage.fixNull(request
								.getParameter("status")));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			int suc = cla.update();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$updateAssociation</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$updateAssociation</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û���޸�������Ϣ��Ȩ��");
		}
	}

	@Override
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

	@Override
	public int addAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().applyAssociationMember == 1) {
			OracleAssociationMember member = null;
			try {
				String associationId = strManage.fixNull(request
						.getParameter("association_id"));
				String memberType = strManage.fixNull(request
						.getParameter("member_type"));
				String linkId = strManage.fixNull(request
						.getParameter("link_id"));
				member = new OracleAssociationMember(linkId, memberType,
						associationId);
			} catch (WhatyUtilException e) {
				throw new PlatformException("addAssociationMember error!("
						+ e.toString() + ")");
			}
			int sub = member.add();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getPriv().getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$addAssociationMember</whaty><whaty>STATUS$|$"
									+ sub
									+ "</whaty><whaty>NOTES$|$addAssociationMember</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("��û�м������ŵ�Ȩ��");
		}
	}

	@Override
	public AssociationMember getAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().getAssociationMember == 1) {
			try {
				return new OracleAssociationMember(strManage.fixNull(request
						.getParameter("id")));
			} catch (Exception e) {
				throw new PlatformException(e.getMessage());
			}
		} else {
			throw new PlatformException("��û�в�ѯ���ų�Ա��Ȩ��");
		}
	}

	@Override
	public AssociationMember getAssociationMember(String id)
			throws PlatformException {
		if (this.getPriv().getAssociationMember == 1) {
			try {
				return new OracleAssociationMember(id);
			} catch (Exception e) {
				throw new PlatformException(e.getMessage());
			}
		} else {
			throw new PlatformException("��û�в�ѯ���ų�Ա��Ȩ��");
		}
	}

	@Override
	public int delAssociationMember(String id) throws PlatformException {
		if (this.getPriv().deleteAssociationMember == 1) {
			OracleAssociationMember cla = new OracleAssociationMember();
			cla.setId(id);
			int sub = cla.delete();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$delAssociationMember</whaty><whaty>STATUS$|$"
							+ sub
							+ "</whaty><whaty>NOTES$|$delAssociationMember</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("��û��ɾ�����ų�Ա��Ȩ��");
		}
	}

	public List getAssociationMemberList(Page page, HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().getAssociationMember == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			OracleAssociationMemberList associationList = new OracleAssociationMemberList();
			try {
				if (!strManage.fixNull(request.getParameter("name")).equals(""))
					searchProperty.add(new SearchProperty("name", request
							.getParameter("name"), "like"));
				if (!strManage.fixNull(request.getParameter("association_id"))
						.equals(""))
					searchProperty.add(new SearchProperty("association_id",
							request.getParameter("association_id"), "="));
				if (!strManage.fixNull(request.getParameter("member_type"))
						.equals(""))
					searchProperty.add(new SearchProperty("member_type",
							request.getParameter("member_type"), "="));
				if (!strManage.fixNull(request.getParameter("link_id")).equals(
						""))
					searchProperty.add(new SearchProperty("link_id", request
							.getParameter("link_id"), "="));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			return associationList.getAssociationMembers(page, searchProperty,
					orderProperty);
		} else {
			throw new PlatformException("��û�в�ѯ���ų�Ա��Ȩ��");
		}
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

	@Override
	public int confirmAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkAssociationMember == 1) {
			OracleAssociationMember cla = new OracleAssociationMember();
			cla.setId(request.getParameter("id"));
			int sub = cla.confirm();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$confirmAssociationMember</whaty><whaty>STATUS$|$"
							+ sub
							+ "</whaty><whaty>NOTES$|$confirmAssociationMember</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
			
		} else {
			throw new PlatformException("��û��������ų�Ա��Ȩ��");
		}
	}

	@Override
	public int unConfirmAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().checkAssociationMember == 1) {
			OracleAssociationMember cla = new OracleAssociationMember();
			cla.setId(request.getParameter("id"));
			int sub = cla.unConfirm();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$unConfirmAssociationMember</whaty><whaty>STATUS$|$"
							+ sub
							+ "</whaty><whaty>NOTES$|$unConfirmAssociationMember</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("��û��������ų�Ա��Ȩ��");
		}
	}

	public int updateAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		if (this.getPriv().updateAssociationMember == 1) {
			OracleAssociationMember cla = new OracleAssociationMember();
			try {
				cla.setId(strManage.fixNull(request.getParameter("id")));
				cla.setName(strManage.fixNull(request.getParameter("name")));
				cla
						.setStatus(strManage.fixNull(request
								.getParameter("status")));
			} catch (WhatyUtilException e) {
				throw new PlatformException();
			}
			int sub = cla.update();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$updateAssociationMember</whaty><whaty>STATUS$|$"
							+ sub
							+ "</whaty><whaty>NOTES$|$updateAssociationMember</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return sub;
		} else {
			throw new PlatformException("��û���޸����ų�Ա��Ϣ��Ȩ��");
		}
	}

	@Override
	public int addMemberToClass(String[] linkId, String memberType,
			String classId) throws PlatformException {
		int suc = 0;
		if (this.getPriv().applyClassMember == 1) {
			if (linkId == null)
				return suc;
			for (int i = 0; i < linkId.length; i++) {
				List templist = new OracleClassMemberList().getClassMembers(
						classId, memberType, linkId[i]);
				if (templist.size() < 1)
					suc += new OracleClassMember(linkId[i], memberType, classId)
							.add();
			}
		} else {
			throw new PlatformException("��û����Ӱ༶��Ա��Ȩ��");
		}
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$addMemberToClass</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$addMemberToClass</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	@Override
	public int addMemberToAssociation(String[] linkId, String memberType,
			String associationId) throws PlatformException {
		int suc = 0;
		if (this.getPriv().applyAssociationMember == 1) {
			if (linkId == null)
				return suc;
			for (int i = 0; i < linkId.length; i++) {
				List templist = new OracleAssociationMemberList()
						.getAssociationMembers(associationId, memberType,
								linkId[i]);
				if (templist.size() < 1)
					suc += new OracleAssociationMember(linkId[i], memberType,
							associationId).add();
			}
		} else {
			throw new PlatformException("��û��������ų�Ա��Ȩ��");
		}
		UserLog
		.setInfo(
				"<whaty>USERID$|$"
						+ this.getPriv().getManagerId()
						+ "</whaty><whaty>BEHAVIOR$|$addMemberToAssociation</whaty><whaty>STATUS$|$"
						+ suc
						+ "</whaty><whaty>NOTES$|$addMemberToAssociation</whaty><whaty>LOGTYPE$|$"
						+ LogType.MANAGER
						+ "</whaty><whaty>PRIORITY$|$"
						+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	@Override
	public int assignClassMemberToManager(String id) throws PlatformException {
		if (this.getPriv().assignClassMemberToManager == 1) {
			OracleClassMember cla = new OracleClassMember();
			cla.setId(id);
			int suc = cla.assignManager();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$assignClassMemberToManager</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$assignClassMemberToManager�ı��Ա��ɫ</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�иı��Ա��ɫ��Ȩ��");
		}
	}

	@Override
	public int unAssignClassMemberToManager(String id) throws PlatformException {
		if (this.getPriv().assignClassMemberToManager == 1) {
			OracleClassMember cla = new OracleClassMember();
			cla.setId(id);
			int suc = cla.unAssignManager();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$unAssignClassMemberToManager</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$unAssignClassMemberToManager�ı��Ա��ɫ</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�иı��Ա��ɫ��Ȩ��");
		}
	}

	@Override
	public int assignAssociationMemberToManager(String id)
			throws PlatformException {
		if (this.getPriv().assignAssociationMemberToManager == 1) {
			OracleAssociationMember cla = new OracleAssociationMember();
			cla.setId(id);
			int suc = cla.assignManager();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$assignAssociationMemberToManager</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$assignAssociationMemberToManager�ı��Ա��ɫ</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�иı��Ա��ɫ��Ȩ��");
		}
	}

	@Override
	public int unAssignAssociationMemberToManager(String id)
			throws PlatformException {
		if (this.getPriv().assignAssociationMemberToManager == 1) {
			OracleAssociationMember cla = new OracleAssociationMember();
			cla.setId(id);
			int suc = cla.unAssignManager();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$unAssignAssociationMemberToManager</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$unAssignAssociationMemberToManager�ı��Ա��ɫ</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�иı��Ա��ɫ��Ȩ��");
		}
	}

	@Override
	public InteractionUserPriv getInteractionUserPriv(String id) {
		InteractionFactory factory = InteractionFactory.getInstance();
		return factory.getInteractionUserPriv(id);
	}

	@Override
	public InteractionManage creatInteractionManage(
			InteractionUserPriv interactionUserPriv) {
		InteractionFactory factory = InteractionFactory.getInstance();
		return factory.creatInteractionManage(interactionUserPriv);
	}

	@Override
	public int creatClassForum(String classId, String forumId)
			throws PlatformException {
		if (this.getPriv().createClassForum == 1) {
			OracleClass cla = new OracleClass(classId);
			cla.setForumId(forumId);
			int suc = cla.updateForumId();
			UserLog
			.setInfo(
					"<whaty>USERID$|$"
							+ this.getPriv().getManagerId()
							+ "</whaty><whaty>BEHAVIOR$|$creatClassForum</whaty><whaty>STATUS$|$"
							+ suc
							+ "</whaty><whaty>NOTES$|$creatClassForum</whaty><whaty>LOGTYPE$|$"
							+ LogType.MANAGER
							+ "</whaty><whaty>PRIORITY$|$"
							+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new PlatformException("��û�д����༶��̳��Ȩ��");
		}
	}

	@Override
	public List getClassForumList(Page page, String classId, boolean isNew,
			int displayNum, String keyword) throws PlatformException {
		List searchProperty = new ArrayList();
		List orderProperty = new ArrayList();
		try {
			if (!strManage.fixNull(classId).equals(""))
				searchProperty.add(new SearchProperty("id", classId, "="));
			if (!strManage.fixNull(keyword).equals(""))
				searchProperty.add(new SearchProperty("content", keyword,
						"like"));
			if (displayNum != -1)
				searchProperty.add(new SearchProperty("rownum", String
						.valueOf(displayNum), "="));
			if (isNew)
				orderProperty.add(new OrderProperty("create_date",
						OrderProperty.DESC));

		} catch (WhatyUtilException e) {
			
		}
		return new OracleClassList().getClassForums(page, searchProperty,
				orderProperty);
	}

	public int getClassForumNum(String classId, String keyword)
			throws PlatformException {
		List searchProperty = new ArrayList();
		try {
			if (!strManage.fixNull(classId).equals(""))
				searchProperty.add(new SearchProperty("id", classId, "="));
			if (!strManage.fixNull(keyword).equals(""))
				searchProperty.add(new SearchProperty("content", keyword,
						"like"));
		} catch (WhatyUtilException e) {
			
		}
		return new OracleClassList().getClassForumesNum(searchProperty);
	}
}
