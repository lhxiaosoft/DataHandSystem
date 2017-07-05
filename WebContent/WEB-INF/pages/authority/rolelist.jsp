<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="edu.csu.filter.SecurityServlet"%>
<%@page import="edu.csu.utils.OpertionEnum"%>
<%@page import="edu.csu.utils.OperationAuthority"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
<%
boolean isAdd = OperationAuthority.getOper(OpertionEnum.ADD);
boolean isUpdate = OperationAuthority.getOper(OpertionEnum.UPDATE);
boolean isDelete = OperationAuthority.getOper(OpertionEnum.DELETE);
boolean isEdit = OperationAuthority.getOper(OpertionEnum.EDIT);
%>
    <title>角色管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyui/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/Common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/js/ztree/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/ztree/js/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/ztree/js/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/ztree/js/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript">

   function formatter_edit(value,obj,index)
   {
	   return "<a class='editcls' href='javascript:void(0)' onclick='editfun("+index+")'>编辑</a>";
   }
   //刷新成功后
   $(function(){
	$('#tt').datagrid({	
		onLoadSuccess:function(data){  
			$('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
			$('.autcls').linkbutton({text:'授权菜单',plain:true,iconCls:'icon-tip'});
	     }
	
	});
   })
  //搜索关键字
	function doSearch(){
		$('#tt').datagrid('load',{
			ROLE_NAME: $('#role_NAME').val(),
		});
	  }
   <%if(isEdit){%>
	   //编辑信息
	   function editfun(index){  
		    $('#tt').datagrid('selectRow',index);// 关键在这里  
		    var row = $('#tt').datagrid('getSelected');  
		    if (row){ 
		        $('#dlg').dialog('open').dialog('setTitle','修改角色信息');  
		        $('#fm').form('load',row);  
		    }  
		}
	   function saveEditRole(){
			$('#fm').form('submit',{
				url: '<%=request.getContextPath() %>/system/adminroleinfo/data_editadminrole',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='ROLE_ISEXIST')
					{
						$.messager.alert("操作提示", "角色名已经存在，请重新输入！");
					}
					else if(result=='true')
					{
						$.messager.alert("操作提示", "角色信息修改成功!");
						$('#dlg').dialog('close');		// close the dialog
						$('#tt').datagrid('reload');	// reload the user data
					}else
						$.messager.alert("操作提示", "保存失败！");
				}
			});
		}
	   <%}%>
	   <%if(isAdd){%>
	   function addRole(){
		   $('#dlg_addRole').dialog('open').dialog('setTitle','添加角色信息');
			$('#fm_addRole').form('clear');
		   }
	   function saveAddRole(){
			$('#fm_addRole').form('submit',{
				url: '<%=request.getContextPath() %>/system/adminroleinfo/data_addadminrole',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='ROLE_ISEXIST')
					{
						$.messager.alert("操作提示", "角色名已经存在，请重新输入！");
					}
					else if(result=='true')
					{
						$.messager.alert("操作提示", "保存成功！");
						$('#dlg_addRole').dialog('close');		// close the dialog
						$('#tt').datagrid('reload');	// reload the user data
					
					}else
						$.messager.alert("操作提示", "保存失败！");
				}
			});
		}
	   <%}%>
	   <%if(isDelete){%>
	   function destroyRole(){
			var row = $('#tt').datagrid('getSelections');
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
				   			temID.push(row[i].role_ID);
				           }
					    $.ajax({
								type:"POST",
								url:'<%=request.getContextPath() %>/system/adminroleinfo/data_deleterole',
								data:"ROLE_ID="+temID,
								async:false,
								success:function(result){
									$('#tt').datagrid('reload');
								},
								error:function(result){
									$.messager.alert("操作提示", "删除失败！");
								}
					   		});
			       		}
			       	else{
			       		$('#tt').datagrid('reload');
				       	}
			    });
		   	}	
		}
	   <%}%>
	   <%if(isUpdate){%>
   //授权链接开始
   function formatter_aut(xxx,obj,index)
   {
	   return "<a class='autcls' href='javascript:void(0)' onclick=autfun('"+obj.role_ID+"')>授权菜单</a>";
   }
   var ROLE_ID;
   function autfun(id)
    {
	   ROLE_ID=id;//保存角色ID
	  $('#function_dialog').dialog('open').dialog('setTitle','授权菜单');
		var url = "<%=request.getContextPath()%>/system/adminroleinfo/data_getalllistEX?ROLE_IDEX="+id;
   		var setting = {
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType:{"Y": "ps", "N": "ps" }
				},
				async: {//异步加载节点
					enable: true,
					url:url
				},
				data: {
					key: {
						name:"menu_NAME"
					},
					simpleData: {
						enable: true,
						idKey: "menu_CODE",
						pIdKey: "menu_PARENT_CODE",
						rootPId: '0'
					}
				}
			};
   		$.fn.zTree.init($("#function_tree"), setting);
	}
   //授权链接结束
   //设置window框开始
   //设置window框结束
   //z-tree开始
 
	$(document).ready(function(){
		$("#getSelectNodes").click(function()
		{
			var treeObj = $.fn.zTree.getZTreeObj("function_tree");
			var nodes = treeObj.getCheckedNodes(true);
			var html = "";
			for(var i=0;i<nodes.length;i++)
			{
				html += nodes[i].id+" "+nodes[i].pId+" "+nodes[i].name+"\r\n";
			}
		});
		$("#setNodes").click(function()
		{
			var treeObj = $.fn.zTree.getZTreeObj("function_tree");
			var node = treeObj.getNodeByParam("id", "21", null);
			treeObj.selectNode(node);
			treeObj.checkNode(node, true, true);
		});
	});
   //z-tree结束
   
    
	   //树的确定按钮开始
	   function sure()
	   {
		   $.messager.progress({ 
   		    text: '正在授权.......' 
   			}); 
		    var treeObj = $.fn.zTree.getZTreeObj("function_tree");
		    var nodes = treeObj.getCheckedNodes(true);
			var html = "";
			for(var i=0;i<nodes.length;i++)
			{
				html += nodes[i].menu_CODE+",";
			}
			$.ajax({
				type:"POST",
				url:"<%=request.getContextPath() %>/system/adminroleinfo/data_suretreeEX",
				data:"IDS="+html+"&ROLE_IDEX="+ROLE_ID,
				dataType:"text",
				success:function(d){
					 $.messager.progress('close');
					$.messager.alert("操作提示", "授权成功！");
					$('#function_dialog').dialog('close');
				},
				error:function(){
					 $.messager.progress('close');
					$.messager.alert("操作提示", "授权失败！");
				}
			});
	   }
	   //树的确定按钮结束
	   <%}%>
	  </script>
  </head>
  <body>
  <!-- 列表开始 -->
    <div id="tb" style="padding:3px"> 
	  	<%if(isAdd){ %>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRole()" >添加</a>
	  	<%}if(isDelete){ %>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRole()" >删除</a>
	  	<%}%>
	  	<span>角色名:</span>
	  	<input  class="easyui-textbox" id="role_NAME" style="line-height:22px;border:1px solid #ccc">
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
    </div> 
  <table id="tt" title="角色管理" class="easyui-datagrid" fit="true" fitColumns ="true" toolbar = "#tb" 
			singleSelect="true" url="system/adminroleinfo/data_getalllist" iconCls="icon-save" pagination="true" rownumbers="true">
		<thead>
			<tr>
			<th field="role_ID" width="100" align="center" checkbox="true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th field="role_CODE" width="200" align="center" sortable="true">角色编码</th>
			<th field="role_NAME" width="250" align="center" sortable="true">角色名称</th>
			<th field="role_DESCRIPTION" width="250" align="center" sortable="true">角色备注</th>
			<%if(isEdit){ %>
			<th  width="200" align="center" field="AAA" formatter="formatter_edit">编辑</th>
			<%}if(isUpdate){ %>
			<th  width="200" align="center" field="aut" formatter="formatter_aut">授权</th>
			<%} %>
			</tr>
		</thead>
	</table>
	<!-- 列表结束 -->	
	
	<!-- 授权框开始 -->
	<div id="function_dialog" class="easyui-dialog" buttons="#statusCol"  style="width: 300px; height: 300px; padding: 10px 20px;" closed="true" 
			data-options="iconCls:'icon-save',cache: false,resizable:true,modal:true"> 
	   <ul id="function_tree" class="ztree"></ul>
    </div>
       <div  id ="statusCol" style="padding:5px 0;text-align:right;padding-right:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-back"  onclick="sure()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-edit"  onclick="$('#function_dialog').dialog('close')">关闭</a>
		</div>
	<!-- 授权框结束 -->
	<!--edit Role弹出框  -->
	<div id="dlg" class="easyui-dialog" style="width:300px;height:215px;padding:10px"
		closed="true" buttons="#dlg-buttons" >
		<form id="fm" method="post">
			<table align="center" style="margin-top:10px;">			
	        	<tr>
					<td><input name="role_ID" type="hidden"></input></td>
				</tr>
				<tr>
					<td>角色编码:</td>
					<td><input class="easyui-textbox" name="role_CODE" type="text" data-options="required:true,validType:{length:[0,15]}" ></input></td>
				</tr>
				<tr>
					<td>角色名称:</td>
					<td><input class="easyui-textbox" name="role_NAME" type="text" data-options="required:true,validType:{length:[0,15]}"></input></td>
				</tr>
				<tr>
					<td>角色备注:</td>
					<td><input class="easyui-textbox" name="role_DESCRIPTION" type="text"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="saveEditRole()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
		<!--add Role弹出框 -->
	<div id="dlg_addRole" class="easyui-dialog" style="width:300px;height:215px;padding:10px"
		closed="true" buttons="#dlg_buttons_addRole" >
		<form id="fm_addRole" method="post">
			<table align="center" style="margin-top:10px;">			
				<tr>
					<td><input name="role_ID" type="hidden"></input></td>
				</tr>
				<tr>
					<td>角色编码:</td>
					<td><input class="easyui-textbox" name="role_CODE" type="text" data-options="required:true,validType:{length:[0,15]}" ></input></td>
				</tr>
				<tr>
					<td>角色名称:</td>
					<td><input class="easyui-textbox" name="role_NAME" type="text" data-options="required:true,validType:{length:[0,15]}"></input></td>
				</tr>
				<tr>
					<td>角色备注:</td>
					<td><input class="easyui-textbox" name="role_DESCRIPTION" type="text"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg_buttons_addRole">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAddRole()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_addRole').dialog('close')">取消</a>
	</div>
  </body>
</html>
