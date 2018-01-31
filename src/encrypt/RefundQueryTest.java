package encrypt;

import java.net.URL;

import com._99bill.www.gatewayapi.services.gatewayRefundQuery.GatewayRefundQueryServiceLocator;
import com._99bill.www.gatewayapi.services.gatewayRefundQuery.GatewayRefundQuerySoapBindingStub;
import com.bill99.seashell.domain.dto.gatewayrefundquery.GatewayRefundQueryRequest;
import com.bill99.seashell.domain.dto.gatewayrefundquery.GatewayRefundQueryResponse;

public class RefundQueryTest {
	//Lee sandbox为测试地址www为生产地址
	private String wsURL = "https://www.99bill.com/gatewayapi/services/gatewayRefundQuery?wsdl";
	
	
//	private  static String version = "v2.0";
//	private  static  String signType = "1";
//	private  static String merchantAcctId = "1001214335701";
//	private  static String startDate = "20130424"; 
//	private  static String endDate = "20130426";
//	private  static String orderId = "20130425171143";  
//	private  static String requestPage = "1";
//	private  static String status = "";
//	private  static String key = "4IQLTUNT365Z6N4K";
	
	//private  static String  signMsg;
	
	
	// signMsg = MD5Util.md5Hex(signMsgVal.getBytes("utf-8")).toUpperCase();
	
//	 public static void main(String[] args) {
//		 
//		 RefundQueryTest   test = new  RefundQueryTest();
//		 
//		 GatewayRefundQueryRequest  request = new GatewayRefundQueryRequest();
//		 
//		 request.setVersion(version);
//		 request.setSignType(signType);
//		 request.setMerchantAcctId(merchantAcctId);
//		 request.setStartDate(startDate);
//			request.setEndDate(endDate);
//			request.setOrderId(orderId);
//			request.setRequestPage(requestPage);
//			request.setStatus(status);
//			request.setSignMsg(signMsg);
//		 
//		 test.query(request);
//		
//	}
	 
	 public GatewayRefundQueryResponse  query(GatewayRefundQueryRequest  request)
	 {
		 
		 try {
				// ȡ��Web ����
			 URL wsdlUrl = new URL(wsURL);
				GatewayRefundQuerySoapBindingStub service = (GatewayRefundQuerySoapBindingStub) new GatewayRefundQueryServiceLocator().getgatewayRefundQuery(wsdlUrl);
				
				// ����WS����
				GatewayRefundQueryResponse result = service.query(request);
				
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	 
	 
//	 public static String appendParam(String returns, String paramId, String paramValue) {
//			if (returns != "") {
//				if (paramValue != null && !paramValue.equals("")) {
//					returns += "&" + paramId + "=" + paramValue;
//				}
//			} else {
//				if (paramValue != null && !paramValue.equals("")) {
//					returns = paramId + "=" + paramValue;
//				}
//			}
//
//			return returns;
//		}
}
