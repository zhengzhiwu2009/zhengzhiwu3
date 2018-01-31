//<---设置Flash随着窗口尺寸变化而变化
var CurHighLightID="";
var LastHighLightID="";
var zhan = "yes";
function resizeFlash()
{
if (parent.PPTSld.player_clip)
{
	
	parent.PPTSld.player_clip.SetVariable('_root.clientWidth',parent.PPTSld.document.body.clientWidth);
	parent.PPTSld.player_clip.SetVariable('_root.clientHeight',parent.PPTSld.document.body.clientHeight);
	parent.PPTSld.player_clip.TCallLabel('_root.ScriptLib','BodyResize');
	
}
}

function Toggle(TreeNode) {
	if (TreeNode.id.substr(0,"TreeMotherNode_".length) != "TreeMotherNode_") return;
	
	var pTreeMotherNode = TreeNode;
	var iNodeID = pTreeMotherNode.id.substr("TreeMotherNode_".length, pTreeMotherNode.id.length-"TreeMotherNode_".length);
	var pDivTreeChildNodes = document.getElementById("divTreeChildNodes_" + iNodeID);
	if (pDivTreeChildNodes.style.display == "none") {
		//展开
		pDivTreeChildNodes.style.display="block";
		pTreeMotherNode.innerHTML=getOpenICON(iNodeID);
	}else{
		pDivTreeChildNodes.style.display="none";
		pTreeMotherNode.innerHTML=getCloseICON(iNodeID);
	}

}

function Expand1() {
  for (i=1; i<=xmlDoc.selectNodes("courseslides/slide").length; i++) {
     divitem=document.getElementById("item" + i);
     if (divitem) divitem.style.display="block";
     key=document.getElementById("xitem" + i);
     if (key) key.innerHTML=getOpenICON(i);
   }
}

function CollapseTree() {
	var pTreeMotherNode;
	var pDivTreeChildNodes;
	zhan = "no";
	for (i=1; i<=xmlDoc.selectNodes("courseslides/slide").length; i++) {
		pTreeMotherNode = document.getElementById("TreeMotherNode_" + i);
		if (pTreeMotherNode){
			pDivTreeChildNodes=document.getElementById("divTreeChildNodes_" + i);
			pDivTreeChildNodes.style.display="none";
			pTreeMotherNode.innerHTML=getCloseICON(i);
		}
	}
}

function ExpandTree() {
	var pTreeMotherNode;
	var pDivTreeChildNodes;
	zhan = "yes";
	for (i=1; i<=xmlDoc.selectNodes("courseslides/slide").length; i++) {
		pTreeMotherNode = document.getElementById("TreeMotherNode_" + i);
		if (pTreeMotherNode){
			pDivTreeChildNodes=document.getElementById("divTreeChildNodes_" + i);
			pDivTreeChildNodes.style.display="block";
			pTreeMotherNode.innerHTML=getOpenICON(i);
		}
	}
}

function Collapse1() {
   
   //for (i=1;i<=xmlDoc.documentElement.childNodes.item(4).nodeTypedValue;i++) {
   for (i=1;i<=xmlDoc.selectNodes("courseslides/slide").length;i++) {
     divitem=document.getElementById("item" + i);
     if (divitem) divitem.style.display="none";
     key=document.getElementById("xitem" + i);
     if (key) key.innerHTML=getCloseICON(i);
   }
}

var tempObjFlashID;
var tempObjID;
function SelectLink(strItemNo)
{

  var pListItem, pListItem1;
	var xmlTimeLineNodes;
	var xmlSlide;
	

	var i;
	var j;
		/*mute 2006.11.27
    for (i=1; i<=xmlDoc.selectNodes("courseslides/slide").length; i++) {
		pListItem = document.getElementById("ListItem_" + i);
		if (pListItem){ 
			pListItem.className = "ListDefault";
		}
		*/
		if(zhan == "yes")
			ExpandTree();
		//add 2006.11.27 解决多节点情况下遍历运算导致cpu100％问题
		if(tempObjFlashID)
			pListItem1 = document.getElementById("ListItem_"+tempObjFlashID);
		if(pListItem1)
			pListItem1.className = "ListDefault";
		if(tempObjID)
			pListItem = document.getElementById("ListItem_"+tempObjID);
		if(pListItem)
		{
			pListItem.className = "ListDefault";
		}
		tempObjID = strItemNo;
		//edit End
		
		
		
		/*mute 2006.11.27
		xmlSlide=xmlDoc.selectNodes("courseslides/slide").item(i-1);
		if (xmlSlide.selectSingleNode('TimeLine') && xmlSlide.selectSingleNode('TimeLine').hasChildNodes()) {
			xmlTimeLineNodes=xmlDoc.selectNodes("courseslides/slide/TimeLine/item")
			for (j=1; j<=xmlTimeLineNodes.length; j++) {
				pListItem = document.getElementById("ListItem_" + i + "_" + j);
				if (pListItem){ 
					pListItem.className = "ListDefault";
				}
			}
		}
    }
    */
   
    pListItem = document.getElementById("ListItem_" + strItemNo);
    if (pListItem)
    {
			pListItem.className = "ListHighLight";
			var tempDiv = document.getElementById("SlideTree");
			var tempTop;
			tempTop = pListItem.offsetTop - tempDiv.offsetHeight/2;
			if(tempTop<0) tempTop=0;
			if((pListItem.offsetTop - tempDiv.scrollTop)>tempDiv.offsetHeight | (pListItem.offsetTop - tempDiv.scrollTop)<0)
			{
				tempDiv.scrollTop = tempTop;
			}
		}
}

function SelectLinkFlash(strPageNo,strFlashNo)
{
 var pListItem, pListItem1;
	var xmlTimeLineNodes;
	var xmlSlide;
	

	var i;
	var j;
		/*mute 2006.11.27
    for (i=1; i<=xmlDoc.selectNodes("courseslides/slide").length; i++) {
		pListItem = document.getElementById("ListItem_" + i);
		if (pListItem){ 
			pListItem.className = "ListDefault";
		}
		*/
		
		
		//add 2006.11.27 解决多节点情况下遍历运算导致cpu100％问题
		if(tempObjID)
			pListItem1 = document.getElementById("ListItem_"+tempObjID);
		if(pListItem1)
		{
			pListItem1.className = "ListDefault";
		}
		
		if(tempObjFlashID)
			pListItem = document.getElementById("ListItem_"+tempObjFlashID);
		if(pListItem)
		{
			pListItem.className = "ListDefault";
		}
		tempObjFlashID = strPageNo+"_"+strFlashNo;
		//edit End
		
		
		
		/*mute 2006.11.27
		xmlSlide=xmlDoc.selectNodes("courseslides/slide").item(i-1);
		if (xmlSlide.selectSingleNode('TimeLine') && xmlSlide.selectSingleNode('TimeLine').hasChildNodes()) {
			xmlTimeLineNodes=xmlDoc.selectNodes("courseslides/slide/TimeLine/item")
			for (j=1; j<=xmlTimeLineNodes.length; j++) {
				pListItem = document.getElementById("ListItem_" + i + "_" + j);
				if (pListItem){ 
					pListItem.className = "ListDefault";
				}
			}
		}
    }
    */
    pListItem = document.getElementById("ListItem_" + strPageNo+"_"+strFlashNo);
    if (pListItem){
		pListItem.className = "ListHighLight";
		}
}

function ChangePicView()
{

	if (SlideTree.style.display=="block") {

		SlideTree.style.display="none";
		imgExpand.style.display="none";
		imgCollapse.style.display="none";
		PicView.style.display="block";
		PicView.style.height="0";
	} else {

		PicView.style.display="none";
		SlideTree.style.display="block";
		SlideTree.style.height="0";
		imgExpand.style.display="block";
		imgCollapse.style.display="block";
	}
	debug("PicView.style.pixelTop="+PicView.style.pixelTop);
	divResize();

}

function ChangePicView1()
{

	if (SlideTree.style.visibility=="visible") {
		SlideTree.style.visibility="hidden";
		imgExpand.style.visibility="hidden";
		imgCollapse.style.visibility="hidden";
//		SlideTree.style.height=0;
		PicView.style.visibility="visible";	 
		debug(PicView.offsetTop);
//		debug(SlideTree.style.display);
	} else {
		PicView.style.visibility="hidden";
		SlideTree.style.visibility="visible";
		imgExpand.style.visibility="visible";
		imgCollapse.style.visibility="visible";
//		PicView.style.height=0;

	}
	divResize();

}

function InitList(IsShowPicView)
{
//	return;
	if (IsShowPicView) {
		SlideTree.style.display="none";
		PicView.style.display="block";	 
		imgCollapse.style.display="none";
		imgExpand.style.display="none";
	} else {
		SlideTree.style.display="block";
		PicView.style.display="none";
	}
//	divResize();
}

function InitList1(IsShowPicView)
{

	if (IsShowPicView) {
		SlideTree.style.visibility="hidden";
//		SlideTree.style.height=0;
		PicView.style.visibility="visible";	 
//		debug(SlideTree.style.display);
		imgCollapse.style.visibility="hidden";
		imgExpand.style.visibility="hidden";
	} else {
		SlideTree.style.visibility="visible";
		PicView.style.visibility="hidden";
		imgExpand.style.visibility="visible";
		imgCollapse.style.visibility="visible";
	}
//	divResize();
}