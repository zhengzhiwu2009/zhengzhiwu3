
var where = new Array(35);
function comefrom(loca, locacity) {
	this.loca = loca;
	this.locacity = locacity;
}
where[0]= new comefrom("",""); 
where[1] = new comefrom("北京市","|东城区|西城区|崇文区|宣武区|朝阳区|丰台区|石景山区|海淀区|门头沟区|房山区|通州区|顺义区|昌平区|大兴区|平谷区|怀柔区|密云区|延庆区");  
where[2] = new comefrom("上海市","|黄浦区|卢湾区|徐汇区|长宁区|静安区|普陀区|闸北区|虹口区|杨浦区|闵行区|宝山区|嘉定区|浦东区|金山区|松江区|青浦区|南汇区|奉贤区|崇明区");  
where[3] = new comefrom("天津市","|和平区|东丽区|河东区|西青区|河西区|津南区|南开区|北辰区|河北区|武清|红挢|塘沽|汉沽|大港|宁河|静海|宝坻|蓟县");  
where[4] = new comefrom("重庆市","|万州区|涪陵区|渝中区|大渡口区|江北区|沙坪坝区|九龙坡区|南岸区|北碚|万盛|双挢|渝北|巴南|黔江|长寿|綦江|潼南|铜梁|大足|荣昌|壁山|梁平|城口|丰都|垫江|武隆|忠县|开县|云阳|奉节|巫山|巫溪|石柱|秀山|酉阳|彭水|江津|合川|永川|南川");  
where[5] = new comefrom("河北省","|石家庄市|邯郸市|邢台市|保定市|张家口市|承德市|廊坊市|唐山市|秦皇岛市|沧州市|衡水市");  
where[6] = new comefrom("山西省","|太原市|大同市|阳泉市|长治市|晋城市|朔州市|吕梁市|忻州市|晋中市|临汾市|运城市");  
where[7] = new comefrom("内蒙古","|呼和浩特市|包头市|乌海市|赤峰市|呼伦贝尔盟市|阿拉善盟市|哲里木盟市|兴安盟市|乌兰察布盟市|锡林郭勒盟市|巴彦淖尔盟市|伊克昭盟市");  
where[8] = new comefrom("辽宁省","|沈阳市|大连市|鞍山市|抚顺市|本溪市|丹东市|锦州市|营口市|阜新市|辽阳市|盘锦市|铁岭市|朝阳市|葫芦岛市");  
where[9] = new comefrom("吉林省","|长春市|吉林市|四平市|辽源市|通化市|白山市|松原市|白城市|延边市");  
where[10] = new comefrom("黑龙江省","|哈尔滨市|齐齐哈尔市|牡丹江市|佳木斯市|大庆市|绥化市|鹤岗市|鸡西市|黑河市|双鸭山市|伊春市|七台河市|大兴安岭市");  
where[11] = new comefrom("江苏省","|南京市|镇江市|苏州市|南通市|扬州市|盐城市|徐州市|连云港市|常州市|无锡市|宿迁市|泰州市|淮安市");  
where[12] = new comefrom("浙江省","|杭州市|宁波市|温州市|嘉兴市|湖州市|绍兴市|金华市|衢州市|舟山市|台州市|丽水市");  
where[13] = new comefrom("安徽省","|合肥市|芜湖市|蚌埠市|马鞍山市|淮北市|铜陵市|安庆市|黄山市|滁州市|宿州市|池州市|淮南市|巢湖市|阜阳市|六安市|宣城市|亳州市");  
where[14] = new comefrom("福建省","|福州市|厦门市|莆田市|三明市|泉州市|漳州市|南平市|龙岩市|宁德市");  
where[15] = new comefrom("江西省","|南昌市|景德镇市|九江市|鹰潭市|萍乡市|新馀市|赣州市|吉安市|宜春市|抚州市|上饶市");  
where[16] = new comefrom("山东省","|济南市|青岛市|淄博市|枣庄市|东营市|烟台市|潍坊市|济宁市|泰安市|威海市|日照市|莱芜市|临沂市|德州市|聊城市|滨州市|菏泽市");  
where[17] = new comefrom("河南省","|郑州市|开封市|洛阳市|平顶山市|安阳市|鹤壁市|新乡市|焦作市|濮阳市|许昌市|漯河市|三门峡市|南阳市|商丘市|信阳市|周口市|驻马店市|济源市");  
where[18] = new comefrom("湖北省","|武汉市|宜昌市|荆州市|襄樊市|黄石市|荆门市|黄冈市|十堰市|恩施市|潜江市|天门市|仙桃市|随州市|咸宁市|孝感市|鄂州市"); 
where[19] = new comefrom("湖南省","|长沙市|常德市|株洲市|湘潭市|衡阳市|岳阳市|邵阳市|益阳市|娄底市|怀化市|郴州市|永州市|湘西市|张家界市");  
where[20] = new comefrom("广东省","|广州市|深圳市|珠海市|汕头市|东莞市|中山市|佛山市|韶关市|江门市|湛江市|茂名市|肇庆市|惠州市|梅州市|汕尾市|河源市|阳江市|清远市|潮州市|揭阳市|云浮市");  
where[21] = new comefrom("广西省","|南宁|柳州|桂林|梧州|北海|防城港|钦州|贵港|玉林|南宁地区|柳州地区|贺州|百色|河池");  
where[22] = new comefrom("海南省","|海口市|三亚市");  
where[23] = new comefrom("四川省","|成都市|绵阳市|德阳市|自贡市|攀枝花市|广元市|内江市|乐山市|南充市|宜宾市|广安市|达川市|雅安市|眉山市|甘孜市|凉山市|泸州市|巴中市");  
where[24] = new comefrom("贵州省","|贵阳市|六盘水市|遵义市|安顺市|铜仁市|黔西南市|毕节市|黔东南市|黔南市");  
where[25] = new comefrom("云南省","|昆明市|大理市|曲靖市|玉溪市|昭通市|楚雄市|红河市|文山市|思茅市|西双版纳市|保山市|德宏市|丽江市|怒江市|迪庆市|临沧市"); 
where[26] = new comefrom("西藏自治区","|拉萨市|日喀则市|山南市|林芝市|昌都市|阿里市|那曲市");  
where[27] = new comefrom("陕西省","|西安市|宝鸡市|咸阳市|铜川市|渭南市|延安市|榆林市|汉中市|安康市|商洛市");  
where[28] = new comefrom("甘肃省","|兰州市|嘉峪关市|金昌市|白银市|天水市|酒泉市|张掖市|武威市|定西市|陇南市|平凉市|庆阳市|临夏市|甘南市");  
where[29] = new comefrom("宁夏自治区","|银川市|石嘴山市|吴忠市|固原市");  
where[30] = new comefrom("青海省","|西宁市|海东市|海南市|海北市|黄南市|玉树市|果洛市|海西市");  
where[31] = new comefrom("新疆自治区","|乌鲁木齐市|石河子市|克拉玛依市|伊犁市|巴音郭勒市|昌吉市|克孜勒苏柯尔克孜市|博尔塔拉市|吐鲁番市|哈密市|喀什市|和田市|阿克苏市");  
where[32] = new comefrom("香港特别行政区","");  
where[33] = new comefrom("澳门特别行政区","");  
where[34] = new comefrom("台湾省","|台北|高雄|台中|台南|屏东|南投|云林|新竹|彰化|苗栗|嘉义|花莲|桃园|宜兰|基隆|台东|金门|马祖|澎湖");  
where[35] = new comefrom("其它","|北美洲|南美洲|亚洲|非洲|欧洲|大洋洲");  
function selectShengShi() {
	if(document.form.province){
		with (document.form.province) {
			var loca2 = options[selectedIndex].value;
		}
	}
	for (i = 0; i < where.length; i++) {
		if (where[i].loca == loca2) {
			loca3 = (where[i].locacity).split("|");
			for (j = 0; j < loca3.length; j++) {
				with (document.form.city) {
					length = loca3.length;
					options[j].text = loca3[j];
					options[j].value = loca3[j];
					var loca4 = options[selectedIndex].value;
				}
			}
			break;
		}
	}
	document.getElementById('hd_address').value = loca2 + loca4;
	document.getElementById('provincex').value = loca2;
	document.getElementById('cityx').value = loca4;

}
function initShengShi(config) {
	var goalP,goal,p,c,d,ss=0,s=0;
	
	if(config){
		goalP=config.goalP; //省份初始值
		goal=config.goal;//城市初始值
		p=config.p;//城市初始值
		c=config.c;//城市初始值
		d=config.d;//城市初始值
	}
	if(p&&c&&d){
		document.getElementById('addressx').value = d.substring((p+c).length);
	}
	
	if($("#isInvoice").length>0 && $(".radioItem").length>0){
		$("#isInvoice").change(
				function() {
					if ($("#isInvoice").val() == '1' || $("#isInvoice").val()== 'y') {
						$("#invoice").css("display","block");
					}else{
						$("#invoice").css("display","none");
					}
		});
		$(".radioItem").change(
			function() {
				var val,$selectedvalue;
				if($(":radio[name='paymentMethod'][checked]").val()){
					val = $(":radio[name='paymentMethod'][checked]").val();
					$selectedvalue = $("input[name='paymentMethod']:checked").val();
				}else if($(":radio[name='payMethod'][checked]").val()){
					val = $(":radio[name='payMethod'][checked]").val();
					$selectedvalue = $("input[name='payMethod']:checked").val();
				}
				if ($selectedvalue == 1 || $selectedvalue == '1') {
					//$("#msg").css("display","block");
					$("#paymentInfo").css("display","none");
					
					$("#op1").attr("selected",true); 
					$("#isInvoice").change();
					$("#isInvoice").attr("disabled",true);
				}else if($selectedvalue != 0 && $selectedvalue != '0'){
					$("#paymentInfo").css("display","block");
					//$("#msg").css("display","none");
					$("#isInvoice").attr("disabled",false); 
				}else{
					//$("#msg").css("display","none");
					$("#paymentInfo").css("display","none");
					$("#isInvoice").attr("disabled",false); 
				}
			}
		);
	}
	if(document.form.province){
		with (document.form.province) {
			length = where.length;
			
			
			for (k = 0; k < where.length; k++) {
				options[k].text = where[k].loca;
				options[k].value = where[k].loca;
				if(goalP && goalP == where[k].loca) {
					ss = k;
				}
			}
			options[ss].selected=true;
			//options[selectedIndex].text = where[ss].loca;
			//options[selectedIndex].value = where[ss].loca;
		}
	}
	if(document.form.city){
		with (document.form.city) {
			loca3 = (where[ss].locacity).split("|");
			length = loca3.length;
			
			for (l = 0; l < length; l++) {
				options[l].text = loca3[l];
				options[l].value = loca3[l];
				if(goal && goal == loca3[l]) {
					s = l;
				}
			}
			options[s].selected=true;
			//options[selectedIndex].text = loca3[s];
			//options[selectedIndex].value = loca3[s];
		}
	}
	selectShengShi();
}

function isSubmit() {
	//订单确认按钮隐藏
	$('#payBtn').hide();
	//提示信息
	$('#tishi').text($('#tishi').text() + '    处理中，请耐心等待！');
	

	var val,$selectedvalue;
	if($(":radio[name='paymentMethod'][checked]").val()){
		val = $(":radio[name='paymentMethod'][checked]").val();
		$selectedvalue = $("input[name='paymentMethod']:checked").val();
	}else if($(":radio[name='payMethod'][checked]").val()){
		val = $(":radio[name='payMethod'][checked]").val();
		$selectedvalue = $("input[name='payMethod']:checked").val();
	}
	var orderAlias = $('#orderAlias').val();
	var isInvoice = $('#isInvoice').val();
	/*
	if(orderAlias == '' || orderAlias == null) {
		alert('备注不能为空');
		return false;
	}
	*/
	if(isInvoice == 1 || isInvoice == 'y') {
		if(!checkInvoice()){//检查发票信息
			return false;
		}
	}
	if(val == null){
		alert("请选择支付方式");
		return false;
	}
	if($selectedvalue == 1 || $selectedvalue == '1') {
		if(typeof balanceTip == "function" && balanceTip()==false){//余额不足提醒
			return false;
		}
	}else if(($selectedvalue == 2)||($selectedvalue == 3) || ($selectedvalue == '2')||($selectedvalue == '3')) {
		if(($('#numNo_con') != null && $('#numNo_con').val() != '')
			||($('#numNo') != null && $('#numNo').val() != '')){
			if( $('#numNo').val() != $('#numNo_con').val()){
				alert('两次输入的汇款单据号不一致，请确认后输入！');
				return false;
			}
		}
	}
	var answer = confirm("确定要支付吗");
	if (answer) {
		$("#form").submit();
	} else {
		return false;
	}
}

function checkInvoice(){
	var invoiceTitle = $('#invoiceTitle').val();
	
	var ss = document.getElementById('hd_address').value;
	var de = document.getElementById('addressx').value;
	document.getElementById('address').value = ss+de;
	var province = $('#province').val();
	var city = $('#city').val();
	var address = $('#address').val();
	var addressx = $('#addressx').val();
	var addressee = $('#addressee').val();
	var post = $('#post').val();
	var tel = $('#tel').val();
	if(invoiceTitle == '' || invoiceTitle == null) {
		alert('付款单位（个人）不能为空');
		return false;
	} 
	if(document.getElementById('ZYFP').checked== true){
	if(province == null || province == '' || province == '请选择省份名') {
		alert('请选择省市');
		return false;
	}
	if(city == null || city == '' || city == '请选择省份名') {
		alert('请选择市');
		return false;
	}
	if(address == '' || address == null) {
		alert('收件地址不能为空');
		return false;
	}
	if(addressx == '' || addressx == null) {
		alert('收件地址不能为空');
		return false;
	}
	
	if(post=='' || post == null) {
		alert('邮政编码不能为空');
		return false;
	}else{
		var pattern=/^[0-9]\d{5}(?!d)$/;
		if(!pattern.test(post)){
			alert("邮编格式不正确！\n请重新输入");
			$('#post').focus();
			return false;
		}
	}
	if(addressee=='' || addressee == null) {
		alert('收件人不能为空');
		return false;
	}else{
		if(addressee.length >10){
			alert('收件人姓名不能大于10个汉字');
			return ;
		}
	}
	if(tel == '' || tel == null) {
		alert('收件人电话不能为空');
		return false;
	} else {
		if(tel.length >20){
			alert('收件人电话不正确，\n请重新输入');
			return;
			}
		}
		//var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;//固话
		//var myreg1=/^1[3|4|5|8][0-9]\d{4,8}$/; //手机
		//var myreg2=/^1[0-9]{10}$/;

		
		//if(!reg.test(tel)&&(!myreg1.test(tel)||!myreg2.test(tel))){
		//	alert("电话号码不正确！\n请重新输入");
		//	$('#tel').focus();
		//	return false;
		//	}
		
	}
	return true;
}

function isInvoice(flag){
 	if(flag=="1"){
 		document.getElementById().style.display="block";
 	}else{
 		document.getElementById().style.display="none";
 	}
 }
function close123() {
	var answer = confirm("确定要关闭吗?");
	if (!answer) {
		return;
	} 
	window.close();
}
