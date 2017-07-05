<%@page import="edu.csu.utils.OpertionEnum"%>
<%@page import="edu.csu.utils.OperationAuthority"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
boolean isAdd = OperationAuthority.getOper(OpertionEnum.ADD);
boolean isUpdate = OperationAuthority.getOper(OpertionEnum.UPDATE);
boolean isDelete = OperationAuthority.getOper(OpertionEnum.DELETE);
boolean isEdit = OperationAuthority.getOper(OpertionEnum.EDIT);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>菜单管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="MENUINFO">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyui/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/Common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
    
  <script type="text/javascript">
/*   function formatter_edit(value,row,index)
  {
	  var r = $('#tt').treegrid("getSelected");
	  return "<a class='editcls' href='javascript:void(0)' onclick='editfun("+row.menu_ID+")'>编辑</a>";
  } */
  //刷新成功后
  $(function(){
	$('#tt').treegrid({	
		onLoadSuccess:function(data){  
			$('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
			$('#tt').treegrid('unselectAll');
		}
	
	});
  })
  //编辑信息
  function editfun(){  
	  var row = $('#tt').treegrid("getSelected");
	  if(row)
	  {
		  $('#dlg').dialog('open').dialog('setTitle','修改菜单信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 430) * 0.5 });;;  
	      $('#fm').form('load',row);  
	      $("#edit_menu_PARENT_CODE").attr("readonly", true);//只可读
	      $("#edit_menu_PARENT_NAME").attr("readonly", true);//只可读
	  }
	  else
		  $.messager.alert("操作提示", "请选择一条记录！");
	   /*   if (row){ 
	       
	    }    */
	}
  function getFormJson(form) {
	  var o = {};
	  var a = $(form).serializeArray();
	  $.each(a, function () {
		  if (o[this.name] !== undefined) {
	  		if (!o[this.name].push) {
	 			 o[this.name] = [o[this.name]];
	  				}
	  	 o[this.name].push(this.value || '');
	  } else {
	     o[this.name] = this.value || '';
	  }
	  });
	  		return o;
	  }
  <%if(isEdit){%>
  function saveEditMenu(){
	  var editedForm =  getFormJson($("#fm"));//拿到已经修改过的表单
		$('#fm').form('submit',{
			url: '<%=request.getContextPath() %>/system/adminmenuinfo/data_editadminmenu',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				if(result=='MENU_ISEXIST')
				{
					$.messager.alert("操作提示", "菜单已经存在，请重新输入！");
				}
				else if(result=='true')
				{
					var cls_State = editedForm.menu_IS_BUTTON=="Y"?'open':'closed';//如果是权限开关，则显示文件夹
					var cls_icon = editedForm.menu_IS_BUTTON=="Y"?'icon-lock':null;
					$('#tt').treegrid('update',{//修改具体
							id: editedForm.menu_ID,
							row: {
								menu_NAME: editedForm.menu_NAME,
								menu_CODE: editedForm.menu_CODE,
								menu_LINK: editedForm.menu_LINK,
								menu_IS_VISIBLE: editedForm.menu_IS_VISIBLE,
								menu_IS_BUTTON: editedForm.menu_IS_BUTTON,
								menu_DESCRIPTION: editedForm.menu_DESCRIPTION,
								state:cls_State,
								iconCls:cls_icon
							}
						});
					$('#dlg').dialog('close');		// close the dialog
					$.messager.alert("操作提示", "菜单信息修改成功!");
					
					//$('#tt').datagrid('reload');	// reload the user data
				}else
					$.messager.alert("操作提示", "保存失败！");
			}
		});
	}
  <%}if(isAdd){%>
  //新增准备函数
  function addMenu(){
	  var row = $('#tt').treegrid("getSelected");
 	 $('#dlg_addMenu').dialog('open').dialog('setTitle','添加菜单信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 430) * 0.5 });;;
 	 $('#addcombo_menu_NAME').hide();
	  if(row)//新建子菜单
	  {
	   var level = $('#tt').treegrid("getLevel",row.id);//拿到层级
	   console.info(level);
	   if(level<2){//新增菜单
			  $('#fm_addMenu').form('clear'); 
			  $('#fm_addMenu').form('load',{ //默认加载父节点名称和CODE
				   menu_PARENT_CODE:row.menu_CODE,
				   menu_PARENT_NAME:row.menu_NAME
					}); 
			  $("#add_menu_PARENT_NAME").attr("readonly", true);//只可读
	   }else{//新增特性
		   $('#addtx_menu_NAME').hide();
		   $('#addcombo_menu_NAME').show();
		   $('#dlg_addMenu').dialog('open').dialog('setTitle','添加特性');
			  $('#fm_addMenu').form('clear'); 
			  $('#fm_addMenu').form('load',{ //默认加载父节点名称和CODE
				   menu_PARENT_CODE:row.menu_CODE,
				   menu_PARENT_NAME:row.menu_NAME
					}); 
		   
	   }
	
	   }else//新建菜单
	   {
		  $('#fm_addMenu').form('clear'); 
		  $('#fm_addMenu').form('load',{ //默认加载父节点名称和CODE
			   menu_PARENT_CODE:'****'		
		  }); 
	   }
		  $("#add_menu_PARENT_CODE").attr("readonly", true);//只可读
  }
  //新增函数
  function saveAddMenu(){
		var addedForm =  getFormJson($("#fm_addMenu"));//拿到已经添加的表单
		$('#fm_addMenu').form('submit',{
			url: '<%=request.getContextPath() %>/system/adminmenuinfo/data_addadminmenu',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				 var row = $('#tt').treegrid("getSelected");//拿到选中项
				 console.info(result);
				if(result=='MENU_ISEXIST')
				{
					$.messager.alert("操作提示", "菜单已经存在，请重新输入！");
				}
				 
				else if(result=='true')
				{
				  if(row){
					var childNode = $('#tt').treegrid("getChildren",row.id);//新建后展开该节点
					if(childNode!=null&&childNode!="")//说明用户已经展开
					{
						var cls_State = addedForm.menu_IS_BUTTON=="Y"?'open':'closed';//如果是权限开关，则显示文件夹
						var cls_icon = addedForm.menu_IS_BUTTON=="Y"?'icon-lock':null;
						$("#tt").treegrid("append",{
							parent: row.id,
							data: [{
								id: addedForm.menu_CODE,
								parentId:row.id,
								menu_ID: addedForm.menu_CODE,//因为默认CODE和ID存一样的字段
								menu_NAME: addedForm.menu_NAME,
								menu_CODE: addedForm.menu_CODE,
								menu_LINK: addedForm.menu_LINK,
								menu_PARENT_CODE: addedForm.menu_PARENT_CODE,
								menu_PARENT_NAME: addedForm.menu_PARENT_NAME,
								menu_IS_VISIBLE: addedForm.menu_IS_VISIBLE,
								menu_IS_BUTTON: addedForm.menu_IS_BUTTON,
								menu_DESCRIPTION: addedForm.menu_DESCRIPTION,
								state : cls_State,
								iconCls:cls_icon
							}]
						}); 
					
					}else//如果没有打开父节点，则自动打开
					{
						$('#tt').treegrid("collapseAll",row.id);//修复打开状态下的新增
						$('#tt').treegrid("expandAll",row.id);
					}
					}else{
						$('#tt').treegrid('reload');
					}
					$.messager.alert("操作提示", "保存成功！");
					$('#dlg_addMenu').dialog('close');	
				}else
					$.messager.alert("操作提示", "保存失败！");
			}
		});
	};
	<%}if(isDelete){%>
  function destroyMenu(){
		var row = $('#tt').treegrid('getSelections');
		var temID = [];
	   	if(row.length==0){
	   		$.messager.alert("操作提示", "请选择要删除的行！");
	   		return;
	   	}
	   	else{
	       	//批量获取选中行的ID
	       	$.messager.confirm('提示','删除为不可逆操作,您确定要进行删除操作吗?',function(r){
		       	if(r){
		       		for(i=0;i<row.length;i++){
		       			temID.push(row[i].id);
		       			var children=$('#tt').treegrid('getChildren',row[i].id);
			   			$.each(children,function(n,value){
			   				temID.push(value.id);
			   				console.info(temID);
			   			});
		       			
			           }
				  <%--   $.ajax({
							type:"POST",
							url:'<%=request.getContextPath() %>/system/adminmenuinfo/data_deletemenu',
							data:"MENU_ID="+temID,
							async:false,
							success:function(result){
							 if(result.Status)
								 {
								    $('#tt').treegrid("remove",row[0].id);//新建后展开该节点
									$.messager.alert("操作提示", "删除成功！");
								 }
							 else
								 $.messager.alert("操作提示", "删除失败！");
								//$('#tt').treegrid('reload');
							},
							error:function(result){
								$.messager.alert("操作提示", "删除失败！");
							}
				   		}); --%>
		       		}
		       	else{
		       		$('#tt').treegrid('reload');
			       	}
		    });
	   	}	
	}
  <%}%>
   function formatter_muneshow(value,name)
   {
 	  if(value=="1"){
 		value="是";
 		return value;
 	  }
 	  else{
 		  value="否";
 		  return '<span style="color:red;">'+value+'</span>';
 	  }
   }   
   function formatter_buttonshow(value,name)
   {
 	  if(value=="Y"){
 		value="是";
 		return value;
 	  }
 	  else{
 		  value="否";
 		  return '<span style="color:red;">'+value+'</span>';
 	  }
   }   
  </script>
   
  </head>
  
  <body>

     <div id="tb" style="padding:3px"> 
       <%if(isAdd) {%>
  	<a href="javascript:void(0)" id="btn_addmenu" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMenu()" >添加</a>
		  <%}if(isEdit){ %>
  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editfun()" >编辑</a>
  	  <%}if(isDelete){ %>
  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyMenu()" >删除</a>
		<%} %>
<!--   	<span>角色名:</span>
  	<input  class="easyui-textbox" id="role_NAME" style="line-height:22px;border:1px solid #ccc">
  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a> -->
   </div> 
   
   

   <table id="tt" title="菜单管理"  fit="true" fitColumns ="true"  toolbar = "#tb" 
		      data-options="
                url: 'system/adminmenuinfo/data_getalllist',
                rownumbers: true,
                pagination: true,
                animate:true,
                pageSize: 20,
                pageList: [20,30,40],
                idField: 'menu_ID',
                treeField: 'menu_NAME',
                onBeforeLoad: function(row,param){
                    if (!row) {    // load top level rows
                        param.id = '****';
                    }
                }
            ">
		<thead>
			<tr>
			<th field="id" width="100" align="right" hidden = "true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th field="parentId" width="100" align="right" hidden = "true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th field="state" width="100" align="right" hidden = "true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th field="menu_ID" width="100" align="right" hidden = "true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th field="menu_NAME" width="200">菜单名称</th>
			<th field="menu_CODE" width="200" align="center" sortable="true">菜单编码</th>
			<th field="menu_LINK" width="300" align="left">菜单链接</th>
			<th field="menu_PARENT_CODE" width="100" align="center">菜单父级编号</th>
			<th field="menu_PARENT_NAME" width="100" align="center">菜单父级名称</th>
			<th field="menu_IS_BUTTON" width="100" align="center" formatter="formatter_buttonshow">是否为按钮</th>
			<th field="menu_IS_VISIBLE" width="100" align="center" formatter="formatter_muneshow">菜单是否显示</th>
			<th field="menu_DESCRIPTION" width="150" align="center">菜单备注</th>
<!--        <th  width="80" align="center" field="AAA" formatter="formatter_edit">操作</th>
 --> 	</tr> 
		</thead>
	</table>
		<!--edit Menu弹出框  -->
	<div id="dlg" class="easyui-dialog" style="width:350px;height:350px;padding:10px"
		closed="true" buttons="#dlg-buttons" >
		<form id="fm" method="post">
			<table  cellpadding="3px" align="center" style="margin-top:10px;">			
	        	<tr>
					<td><input name="menu_ID" type="hidden"></input></td>
				</tr>
				<tr>
					<td>菜单编码:</td>
					<td><input class="easyui-textbox" name="menu_CODE" type="text" data-options="required:true,validType:{length:[0,15]}" ></input></td>
				</tr>
				<tr>
					<td>菜单名称:</td>
					<td><input class="easyui-textbox" name="menu_NAME" type="text" data-options="required:true,validType:{length:[0,15]}"></input></td>
				</tr>
					<tr>
					<td>菜单链接:</td>
					<td><input class="easyui-textbox" name="menu_LINK" type="text" ></input></td>
				</tr>
					<tr>
					<td>菜单父级编号:</td>
					<td><input class="easyui-textbox" name="menu_PARENT_CODE" id="edit_menu_PARENT_CODE" type="text" data-options="readonly:true,validType:{length:[0,15]}"></input></td>
				</tr>
					<tr>
					<td>菜单父级名称:</td>
					<td><input class="easyui-textbox" name="menu_PARENT_NAME" id="edit_menu_PARENT_NAME" type="text" ></input></td>
				</tr>
				<tr>
					<td>是否为按钮:</td>
					<td>
						<select class="easyui-combobox" name="menu_IS_BUTTON" data-options="required:true,editable:false" style="width:148px;" panelHeight="auto">
							<option value="Y">是</option>
							<option value="N">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>菜单是否显示:</td>
					<td>
						<select class="easyui-combobox" name="menu_IS_VISIBLE" data-options="required:true,editable:false" style="width:148px;" panelHeight="auto">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>菜单备注</td>
					<td><input class="easyui-textbox" name="menu_DESCRIPTION" type="text"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="saveEditMenu()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
		<!--add Menu弹出框 -->
	<div id="dlg_addMenu" class="easyui-dialog"  style="width:350px;height:350px;padding:10px"
		closed="true" buttons="#dlg_buttons_addMenu" >
		<form id="fm_addMenu" method="post">
			<table  cellpadding="3px"  align="center" style="margin-top:10px;">			
				<tr>
					<td><input name="menu_ID" type="hidden"></input></td>
				</tr>
				<tr>
					<td>菜单编码:</td>
					<td><input class="easyui-textbox" name="menu_CODE" type="text" data-options="required:true,validType:{length:[0,15]}" ></input></td>
				</tr>
				<tr>
					<td>菜单名称:</td>
					<td id = "addtx_menu_NAME">
					<input class="easyui-textbox"  name="menu_NAME" type="text" data-options="required:true,validType:{length:[0,15]}"></input></td>
					</td>
				</tr>
					<tr>
					<td>菜单链接:</td>
					<td><input class="easyui-textbox" name="menu_LINK" type="text" ></input></td>
				</tr>
					<tr>
					<td>菜单父级编号:</td>
					<td><input class="easyui-textbox" name="menu_PARENT_CODE" id="add_menu_PARENT_CODE" type="text" data-options="required:true,validType:{length:[0,15]}"></input></td>
				</tr>
					<tr>
					<td>菜单父级名称:</td>
					<td><input class="easyui-textbox" name="menu_PARENT_NAME" id="add_menu_PARENT_NAME" type="text" ></input></td>
				</tr>
				<tr>
					<td>是否为按钮:</td>
					<td>
						<select class="easyui-combobox" name="menu_IS_BUTTON" data-options="required:true,editable:false" style="width:148px;" panelHeight="auto">
							<option value="Y">是</option>
							<option value="N">否</option>
						</select>
					</td>
				</tr>
					<tr>
					<td>菜单是否显示:</td>
					<td>
						<select class="easyui-combobox" name="menu_IS_VISIBLE" data-options="required:true,editable:false" style="width:148px;" panelHeight="auto">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>菜单备注</td>
					<td><input class="easyui-textbox" name="menu_DESCRIPTION" type="text"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg_buttons_addMenu">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAddMenu()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_addMenu').dialog('close')">取消</a>
	</div>
  </body>
 
</html>
