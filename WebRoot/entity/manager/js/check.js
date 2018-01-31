
//??????<script language=javascript src=../../check.js></script>???html???????????????
//??????x???????????????
function isInt(x)
{
	var isRight=true;
	for (var i=0;i<x.length;i++)
	{
		var k = x.charAt(i);
		if ((k<'0') || (k>'9'))
		{
			isRight=false;
			break;
		}
	}
	return isRight;
}

function isScore(x)
{
	if(isInt(x) && x.length<=2 && isIn0_99(x))
		return true;
	return false;
}

function isIn0_99(x)
{
	if(isInt(x))
	{
		if(x >= 0 && x <= 99)
			return true;
	}
	return false;
}


function isNum(x)
{
	var isRight=true;
	var count = 0;
	for (var i=0;i<x.length;i++)
	{
		var k = x.charAt(i);
		if ((k<'0' && k!='.') || (k>'9' && k!='.' ))
		{
			isRight=false;
			break;
		}
		if(k=='.')
			count++;
	}
	if(count>1)
	{
		isRight=false;
	}	
	return isRight;
}

function isNum2(item){
	var pattern = /^[0-9]*$/;
	if( item == "" ) return true;
	return pattern.test(item);
}



//??????x????????????
function isNull(x){
	var isRight=true;
	if (x=="")
	{
		isRight=false;
	}
	return isRight;
}

//??????x??????????????????i
function Length(x,i)
{
	var isRight=false;
	if (x.length==parseInt(i))
	{
		isRight=true;
	}
	return isRight;
}

//?????????????????????x?????????????????????y
function comparestr_equal(x,y)
{
	var isRight=false;
	if (x==y)
	{
		isRight=true;
	}
	return isRight;
}

//??????????????????x????????????y
function compare_greater(x,y)
{
	var isRight=false;
	if (parseInt(x)>parseInt(y))
	{
		isRight=true;
	}
	return isRight;
}

//??????????????????x????????????y
function compare_equal(x,y)
{
	var isRight=false;
	if (parseInt(x)==parseInt(y))
	{
		isRight=true;
	}
	return isRight;
}

//??????????????????x????????????y
function compare_less(x,y)
{
	var isRight=false;
	if (parseInt(x)<parseInt(y))
	{
		isRight=true;
	}
	return isRight;
}

//????????????x?????????y,??????x>y ??????1;x=y ??????0;x<y ??????-1
function compare_num(x,y)
{
	var num=1;
	if (parseInt(x)<parseInt(y))
	{
		num=-1;
	}
	else if(parseInt(x)=parseInt(y))
	{
		num=0;
	}
	else
	{
		num=1;
	}
	return num;
}

//?????????????????????
function validIdCard(x)
{
	var isRight=true;
	var xLength=x.length;
	
	if (xLength==15)
	{
		for (var i=0;i<xLength;i++)
		{
			var k = x.charAt(i);
			if ((k<'0' && k!='.') || (k>'9' && k!='.' ))
			{
				isRight=false;
				break;
			}
		}
	}
	else if (xLength==18)
	{
		for (var i=0;i<xLength;i++)
		{
			var k = x.charAt(i);
			if (i!=xLength-1)
			{
				if (k<'0' || k>'9')
				{
					isRight=false;
					break;
				}
			}
			else
			{
				if ((k<'0' || k>'9') && (k<'a' || k>'z') && (k<'A' || k >'Z'))
				{
					isRight=false;
					break;
				}
			}
		}
	}
	else
	{
		isRight=false;
	}
	
	return isRight;
}

function isZero(x)
{
	var isRight=true;
	
	if (x == 0)
	{
		isRight=false;
	}
	return isRight;
}

//??????checkbox????????????
function checkboxIsNull(x)
{
	var isRight=true;
	var xLength=x.length;
	
	for(var i=0;i<xLength;i++)
	{
		if(x[i].checked==true)
		{
			isRight=false;
			break;
		}
	}
	return isRight;
}

//????????????x?????????y ,??????x???y??? ??????1;x=y ??????0;x???y??? ??????-1(???????????????yyyy-mm-dd)
function comparedate(x,y)
{
	year1=x.substring(0,4);
	month1=x.substring(5,7);
	day1=x.substring(8,10);
	date1=new Date(parseInt(year1),parseInt(month1)-1,parseInt(day1));
	year1=y.substring(0,4);
	month1=y.substring(5,7);
	day1=y.substring(8,10);
	date2=new Date(parseInt(year1),parseInt(month1)-1,parseInt(day1));
	if(date1.getTime()<date2.getTime())
		isRight=true;
	else 
		isRight=false;
	return isRight;
}
//????????????
/*function cfmdel(url)
{
	if(confirm("????????????????????????"))
		window.navigate(url);
}
*/
