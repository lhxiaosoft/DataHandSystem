$(function(){
	InitLeftMenu();
	tabClose();
	tabCloseEven();




	$('#tabs').tabs({
        onSelect: function (title) {
            var currTab = $('#tabs').tabs('getTab', title);
            var iframe = $(currTab.panel('options').content);

			var src = iframe.attr('src');
			

        }
    });

})

//初始化左侧
var treeids = new Array();;
function InitLeftMenu() {
	$("#nav").accordion({animate:false});
	
    $.each(_menus.menus, function(i, n) {
		var menulist ='';
		treeids[i]=n.menu_CODE;
		menulist +='<ul class="ztree" id="tree'+n.menu_CODE+'">';
		/*
        $.each(n.menus, function(j, o) {
			menulist += '<li><div><a ref="'+o.menu_CODE+'" href="#" rel="' + o.menu_LINK + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menu_NAME + '</span></a></div></li> ';
        });
        */
		menulist += '</ul>';
		$('#nav').accordion('add', {
            title: n.menu_NAME,
            content: menulist,//'<ul id="treeDemo" class="ztree"></ul>'
            iconCls: 'icon ' + n.icon
        });
    });
    /*
	$('.easyui-accordion li a').click(function(){
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(menuid,icon);

		addTab(tabTitle,url,icon);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});
     */
	//选中第一个
	var panels = $('#nav').accordion('panels');
	if (panels[0]!=null) {
		var t = panels[0].panel('options').title;
		$('#nav').accordion('select', t);
	}
   
    //initZTree
   // initZTree();
    initZTree(treeids);
    
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menu_CODE==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出+
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

//z-tree
function initZTree(_ids){
	for(var i=0;i<_ids.length;i++)
	{
		var setting = {
				view: {
					showLine: false
				},
			async: {
				enable: true,
				url:"data_getchildrens?pId="+_ids[i],
				dataFilter: ajaxDataFilter
			},
			data: {
				key: {
					name:"menu_NAME"
				},
				simpleData: {
					enable: true,
					idKey: "menu_ID",
					pIdKey: "menu_PARENT_CODE",
					rootPId: 0
				}
			},
			callback: {
				onClick: onClick
			}
		};
		$.fn.zTree.init($("#tree"+_ids[i]), setting);
	}
}
function ajaxDataFilter(treeId, parentNode, responseData)
{
	 if (responseData) 
	 {
	      for(var i =0; i < responseData.length; i++) 
	      {
	    	  responseData[i].iconOpen = "../../js/easyuicji/images/open.png";
	    	  responseData[i].iconClose = "../../js/easyuicji/images/close.png";
	    	  responseData[i].icon = "../../js/easyuicji/images/children.png";
	    	  responseData[i].menu_LINK = _baseurl+responseData[i].menu_LINK;
	      }
	      
	 }
	 
	 
	 return responseData;
}
function onClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.expandNode(treeNode);
	var node = zTree.getNodeByParam("menu_PARENT_CODE", treeNode.menu_CODE, null);
	if(node==null)
	{
		var tabTitle = treeNode.menu_NAME;
		var url = treeNode.menu_LINK;
		addTab(tabTitle,url,"");
	}
}

