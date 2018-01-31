package com.whaty.platform.quartz;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ctc.wstx.util.StringUtil;
import com.whaty.platform.entity.bean.OnlineOrderInfo;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.util.PaymentUtil;
import com.whaty.platform.entity.web.util.Base64Util;
import com.whaty.platform.entity.web.util.WebServiceInvoker;
import com.whaty.platform.entity.web.util.XMLFileUtil;
import com.whaty.util.SpringUtil;

/**
 * 纸质发票自动确认定时任务
 * 
 */
public class QuartzInvoicePaperJob extends QuartzJobBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private QuartzService quartzService;
	private GeneralService generalService;
	private EnumConstService enumConstService;

	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}

	public GeneralService getGeneralService() {
		if (generalService == null) {
			generalService = (GeneralService) SpringUtil
					.getBean("generalService");
		}
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public QuartzService getQuartzService() {
		if (quartzService == null) {
			quartzService = (QuartzService) SpringUtil.getBean("quartzService");
		}
		return quartzService;
	}

	public EnumConstService getEnumConstService() {
		if (enumConstService == null) {
			enumConstService = (EnumConstService) SpringUtil
					.getBean("enumConstService");
		}
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long start = new Date().getTime();
		this.confirmInvoice();
		long end = new Date().getTime();
		System.out.println("发票定时任务" + sdf.format(new Date()) + "完成，耗时："
				+ (end - start) + "ms");
	}

	/**
	 * 确认发票
	 * 
	 * @param
	 * @return
	 * @author linjie
	 */
	private List confirmInvoice() {
		// 查询所有代开发票
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("enumConstByFlagFpOpenState", "enumConstByFlagFpOpenState");// 开具状态
		dc.add(Restrictions.eq("enumConstByFlagFpOpenState.code", "0"));
		dc.createCriteria("enumConstByInvoiceType", "enumConstByInvoiceType");
		dc.add(Restrictions.eq("enumConstByInvoiceType.code", "4"));
		dc.add(Restrictions.isNotNull("invoiceNum"));
		dc.addOrder(Order.asc("count"));
		try {
			Page page = this.getGeneralService()
					.getByPage(dc, 100, 0);
			List<PeBzzInvoiceInfo> peInvoiceList = page.getItems();
			for(PeBzzInvoiceInfo pi : peInvoiceList) {
			}
			XMLFileUtil xfu = new XMLFileUtil();
			// 拼xml
			String xml = xfu.getXMLQuery(peInvoiceList);
			if (null != xml && !"".equals(xml)) {
				// 调接口
				WebServiceInvoker wsi = new WebServiceInvoker();
				String wsReturn = wsi.client(xml);
				Map<String, Object> returnMap = XMLFileUtil
						.parseQueryXml(wsReturn);
				if("0000".equals(returnMap.get("returnCode"))) {
					if(null != peInvoiceList && peInvoiceList.size() >0){
						for(PeBzzInvoiceInfo info :peInvoiceList){
							if(null != info.getCount()){
								info.setCount(String.valueOf(Integer.parseInt(info.getCount())+ 1));
								this.generalService.save(info);
							}
						}
					}
					if (null != (List<Map<String, String>>)returnMap.get("returnInfo") && ((List<Map<String, String>>)returnMap.get("returnInfo")).size() > 0) {
						List<Map> successList = new ArrayList<Map>();
						for (Map<String, String> map : (List<Map<String, String>>)returnMap.get("returnInfo")) {
							if (map.containsKey("FPDM") && map.containsKey("FPHM") 
									&& null != map.get("FPDM")
									&& !"".equals(map.get("FPDM"))
									&& null != map.get("FPHM")
									&& !"".equals(map.get("FPHM"))) {
								successList.add(map);
							}
						}
						if (null != successList && successList.size() > 0) {
							this.getGeneralService().uptInvoiceState(successList);
						}
					}
				} else {
					try {
						System.out.println(new String(Base64Util.getFromBASE64(((String)returnMap.get("returnMsg"))).getBytes(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("纸质发票查询xml为空");
			}
			System.out.println("纸质发票自动确认任务完成！！");
		} catch (EntityException e) {
			System.out.println("纸质发票自动确认失败！！--" + e.getMessage());
		}
		return null;
	}
}
