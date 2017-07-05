<%@page import="edu.csu.filter.SecurityServlet"%>
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
    <title>用户管理</title>
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
    $.extend($.fn.validatebox.defaults.rules, {
        account: {//param的值为[]中值
            validator: function (value, param) {
                if (value.length < param[0] || value.length > param[1]) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                    return false;
                } else {
                    if (!/^[\w]+$/.test(value)) {
                        $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
                        return false;
                    } else {
                        return true;
                    }
                }
            }, message: ''
        }
    });
    	/* 格式化日期控件 */
		function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
		function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
	   function formatter_edit(value,obj,index)
	   {
		   return "<a class='editcls' href='javascript:void(0)' onclick='editfun("+index+")'>编辑</a>";
	   }
	   //刷新成功后
	   $(function(){
		$("#user_login_name_flag").attr("readonly", true);//只可读
		$('#tt').datagrid({	
			onLoadSuccess:function(data){  
				$('.autcls').linkbutton({text:'授权角色',plain:true,iconCls:'icon-man'});
				$('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
		     }
		
		});
	   })
	   //搜索
		function doSearch(){
			$('#tt').datagrid('load',{
				USER_LOGIN_NAME: $('#USER_LOGIN_NAME').val()
			});
	   }
	   //编辑信息
	   function editfun(index){  
		    $('#tt').datagrid('selectRow',index);// 关键在这里  
		    var row = $('#tt').datagrid('getSelected');  
		    if (row){ 
		        $('#dlg').dialog('open').dialog('setTitle','修改用户信息');  
		        $('#fm').form('load',row);  
		    }  
		}
	   function saveEditUser(){
			$('#fm').form('submit',{
				url: '<%=request.getContextPath() %>/system/adminuserinfo/data_editadminuser',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='USER_ISEXIST')
					{
						$.messager.alert("操作提示", "用户名已经存在，请重新输入！");
					}
					else if(result=='true')
					{
					$.messager.alert("操作提示", "用户信息修改成功!");
							$('#dlg').dialog('close');		// close the dialog
							$('#tt').datagrid('reload');	// reload the user data
					}else
						$.messager.alert("操作提示", "保存失败！");
					
				}
			});
		}
	   function addUser(){
		   $('#dlg_addUser').dialog('open').dialog('setTitle','添加用户信息');
			$('#fm_addUser').form('clear');
			$('#add_user_createtime').datebox('setValue', formatterDate(new Date()));
		   }
	   function saveAddUser(){
			$('#fm_addUser').form('submit',{
				url: '<%=request.getContextPath() %>/system/adminuserinfo/data_addadminuser',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='USER_ISEXIST')
					{
						$.messager.alert("操作提示", "用户名已经存在，请重新输入！");
					}
					else if(result=='true')
					{
						$.messager.alert("操作提示", "保存成功！");
						$('#dlg_addUser').dialog('close');		// close the dialog
						$('#tt').datagrid('reload');	// reload the user data
					
					}else
						$.messager.alert("操作提示", "保存失败！");
				}
			});
		}
	   function destroyUser(){
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
				   			temID.push(row[i].user_ID);
				           }
					    $.ajax({
								type:"POST",
								url:'<%=request.getContextPath() %>/system/adminuserinfo/data_deleteuser',
								data:"USER_ID="+temID,
								async:false,
								success:function(result){
									$('#tt').datagrid('reload');
								},
								error:function(result){
									$.messager.alert("操作提示", "删除失败！");
								}
					   		});
			       		}
			    });
		   	}	
		}
	   //授权链接开始
	   function formatter_aut(value,obj,index)
	   {
		   return "<a class='autcls' href='javascript:void(0)' onclick=autfun('"+obj.user_ID+"')>授权角色</a>";
	   }
	   var USER_ID;
	   	function autfun(id)
		{
	   		USER_ID=id;
	   		$('#function_dialog').dialog('open').dialog('setTitle','授权角色');
	   		var url = "<%=request.getContextPath()%>/system/adminuserinfo/data_getalllistEX?USER_ID="+id;
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
   						name:"role_NAME"
   					},
   					simpleData: {
   						enable: true,
   						idKey: "role_ID",
   						pIdKey: "pId",
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
		    var treeObj = $.fn.zTree.getZTreeObj("function_tree");
		    var nodes = treeObj.getCheckedNodes(true);
			var html = "";
			if(nodes.length==0)
			{
				$.messager.alert("操作提示", "请选择一个角色！");
				return ;
			}
			for(var i=0;i<nodes.length;i++)
			{
				html += nodes[i].role_ID+",";
			}
			$.ajax({
				type:"POST",
				url:"<%=request.getContextPath() %>/system/adminuserinfo/data_suretreeEX",
				data:"IDS="+html+"&USER_ID="+USER_ID,
				dataType:"text",
				success:function(d){
					if(d)
					{
						$.messager.alert("操作提示", "授权成功！");
					}
					else
						$.messager.alert("操作提示", d);
					$('#function_dialog').dialog('close');
					},
				error:function(){
					$.messager.alert("操作提示", "授权失败！");
					}
			});
	   }
	   //树的确定按钮结束
	  </script>
   
  </head>
  
  <body>
      <%if(isAdd) {%>
  <%} %>
  
   <!-- 列表开始 -->
   <div id="tb" style="padding:3px"> 
    <%if(isAdd) {%>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser()" >添加</a>
	   <%} %>
	  <%if(isDelete) {%>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()" >删除</a>
	  <%} %>
	  	<span>用户名:</span>
	  	<input  class="easyui-textbox" id="USER_LOGIN_NAME" style="line-height:22px;border:1px solid #ccc"/>
	  	<!-- <span>创建时间:</span>
	  	<input class="easyui-datebox"  id="USER_CREATETIME_FLAG" value="new date()" data-options="formatter:myformatter,parser:myparser"/> -->
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
    </div> 
  <table id="tt" title="后台用户管理" class="easyui-datagrid" fit="true" fitColumns ="true" toolbar = "#tb" 
			singleSelect="true" url="system/adminuserinfo/data_getalllist" iconCls="icon-man" pagination="true"  rownumbers="true">
		<thead>
			<tr>
			 <%if(isDelete) {%>
			<th field="user_ID" width="100" align="center" checkbox="true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<%}else{%>
			<th field="user_ID" width="100" align="center" >编号</th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<%} %>
			<th field="user_LOGIN_NAME" width="220" align="center" sortable="true">管理员名称</th>
			<th field="name" width="220" align="center" sortable="true">姓名</th>
			<th field="user_LOGIN_PASSWORD" width="250" sortable="true" align="center">管理员密码</th>
			<th field="user_CREATETIME" width="250" sortable="true" align="center">管理员创建时间</th>
			<%if(isEdit){ %>
			<th  width="150" align="center" field="opr" formatter="formatter_edit">操作</th>
			<% } %>
			<%if(isUpdate){ %>
			<th  width="150" align="center" field="aut" formatter="formatter_aut">授权</th>
			<%} %>
			</tr>
		</thead>
	</table>
	<!-- 列表结束 -->
	<!-- 授权框开始 -->
		<div id="function_dialog" class="easyui-dialog" buttons="#statusCol"  style="width: 300px; height: 210px; padding: 10px 20px;" closed="true" 
			data-options="iconCls:'icon-save',cache: false,resizable:true,modal:true"> 
	       <ul id="function_tree" class="ztree"></ul>
	    </div>
       <div  id ="statusCol" style="padding:5px 0;text-align:right;padding-right:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-back"  onclick="sure()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-edit"  onclick="$('#function_dialog').dialog('close')">关闭</a>
		</div>
	<!-- 授权框结束 -->
	<%if(isEdit) {%>
	<!--edit user弹出框  -->
	<div id="dlg" class="easyui-dialog" style="width:350px;height:210px;padding:10px"
		closed="true" buttons="#dlg-buttons" >
		<form id="fm" method="post">
			<table cellpadding="3px" align="center" style="margin-top:10px;">			
	        	<tr>
					<td><input name="user_ID" type="hidden"></input></td>
					
				</tr>
				<tr>
					<td>用户名:</td>
					<td><input class="easyui-textbox" id ="user_login_name_flag" name="user_LOGIN_NAME" type="text" data-options="required:true" validType="account[5,20]"></input></td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td><input class="easyui-textbox" id ="user_login_name_flag" name="name" type="text" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td><input name="user_LOGIN_PASSWORD" type="hidden"></input></td>
				</tr> 
				<tr>
					<td>创建时间:</td>
					<td>
					<input class="easyui-datebox"  name="user_CREATETIME" data-options="formatter:myformatter,parser:myparser"></input>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="saveEditUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<%} %>
		<!--add user弹出框 -->
	<%if(isAdd) {%>
	<div id="dlg_addUser" class="easyui-dialog" style="width:350px;height:245px;padding:10px"
		closed="true" buttons="#dlg_buttons_addUser" >
		<form id="fm_addUser" method="post">
			<table  cellpadding="3px"  align="center" style="margin-top:10px;">			
				<tr>
					<td><input name="user_ID" type="hidden"></input></td>
				</tr>
				<tr>
					<td>用户名:</td>
					<td><input class="easyui-textbox" name="user_LOGIN_NAME" type="text" data-options="required:true" validType="account[5,20]"></input></td>
				</tr>
				<tr>
					<td>姓名:</td>
					<td><input class="easyui-textbox" id ="user_login_name_flag" name="name" type="text" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>用户密码:</td>
					<td><input class="easyui-textbox" name="user_LOGIN_PASSWORD" type="password" data-options="required:true,validType:{length:[6,15]}"></input></td>
				</tr>
				<tr>
					<td>创建时间:</td>
					<td>
					<input class="easyui-datebox"  id = "add_user_createtime" name="user_CREATETIME"   data-options="formatter:myformatter,parser:myparser"></input>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg_buttons_addUser">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAddUser()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_addUser').dialog('close')">取消</a>
	</div>
	<%} %>
  </body>
</html>
