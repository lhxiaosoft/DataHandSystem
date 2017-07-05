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
    <title>招生点信息</title>
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/js/easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/js/easyui/themes/icon.css">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/easyui/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/Common.js"></script>
		
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
    
  
  //行选中事件
  $(function(){
 	/* $('#tt_recpoint').datagrid({
		  
		  onClickRow: function(index, row){
			  $('#tt_retmoneyinfovo').datagrid('load',{
				  	REC_ID:row.rec_ID
				});
		  }
	  }); */
	  $('#tt_recpoint').datagrid({
		  
		  onClickRow: function(index, row){
			  var opts = $("#tt_retmoneyinfovo").datagrid("options");   //获取从表datagrid
			  opts.url = "<%=request.getContextPath() %>/system/retmoneyinfovo/data_getRetMoneyInfoVoList";   //为从表请求数据路径赋值
			  $('#tt_retmoneyinfovo').datagrid('load',{
				  	REC_ID:row.rec_ID
				});
		  }
	  });
  })
    
    
    //////////////////////////////////////////////////////////
	//编辑信息
   function formatter_editrecpoint(value,obj,index)
   {
	   return "<a class='editcls' href='javascript:void(0)' onclick='editrecpoint_fun("+index+")'>编辑</a>";
   }
   function editrecpoint_fun(index){ 
	    $('#tt_recpoint').datagrid('selectRow',index);// 关键在这里  
	    var row = $('#tt_recpoint').datagrid('getSelected');  
	    if (row){ 
	        $('#dlg_editrecpoint').dialog('open').dialog('setTitle','编辑招生点信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });  
	        $('#fm_editrecpoint').form('load',row);  
	    }  
	}
	   function save_editrecpoint(){
			$('#fm_editrecpoint').form('submit',{
				url: '<%=request.getContextPath() %>/system/recpoint/data_editRecPoint',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='true')
					{
							$.messager.alert("操作提示", "招生点信息修改成功!");
							$('#dlg_editrecpoint').dialog('close');		// close the dialog
							$('#tt_recpoint').datagrid('reload');	// reload the Source data
					}else
						$.messager.alert("操作提示", "保存失败！");
					
				}
			});
		}
		//////////////////////////////////////////////////////////
		
		
	   //刷新主列表页面
	   $(function(){
		$('#tt_recpoint').datagrid({	
			onLoadSuccess:function(data){  
				$('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
				 /* $('#tt_recpoint').datagrid("clearSelections"); */
				$('#tt_retmoneyinfovo').datagrid('loadData',{total:0,rows:[]}); 
		     }
		});
	   })
	   //搜索
		function doSearch_recpoint(){
			$('#tt_recpoint').datagrid('load',{
				/* REC_NAME: $('#REC_NAME').val(),
				REC_YEAR: $('#REC_YEAR').val() */
				REC_YEAR: $('#FIND_REC_YEAR').combobox('getValue'),
				REC_NAME: $('#FIND_REC_NAME').combobox('getValue')
			});
	   }

	   //////////////////////////////////////////////////////////////
	   //新增招生点信息
	   function add_recpoint(){
		   $('#dlg_addrecpoint').dialog('open').dialog('setTitle','新增招生点信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });
			$('#fm_addrecpoint').form('clear');
			$('#REC_SHOUNIT_PRICE').textbox('setValue', '36000');
		   }
	   function save_addrecpoint(){
			$('#fm_addrecpoint').form('submit',{
				url: '<%=request.getContextPath() %>/system/recpoint/data_addRecPoint',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='true')
					{
						$.messager.alert("操作提示", "招生点信息新增成功！");
						$('#dlg_addrecpoint').dialog('close');		// close the dialog
						$('#tt_recpoint').datagrid('reload');			// reload the Source data
					
					}else
						$.messager.alert("操作提示", "保存失败！");
				}
			});
		}
		//////////////////////////////////////////////////////////////
	   
	   //////////////////////////////////////////////////////////////
	   //删除招生点信息
	   function delete_recpoint(){
			var row = $('#tt_recpoint').datagrid('getSelections');
			var temID = [];
		   	if(row.length==0){
		   		$.messager,alert("请选择要删除的行");
		   		return;
		   	}
		   	else{
		       	//批量获取选中行的ID
		       	$.messager.confirm('提示','删除为不可逆操作，<font color="red">删除该招生点，该招生点的学生信息和返款信息也将被删除</font>，您确定要进行删除操作吗?',function(r){
			       	if(r){
			       		for(i=0;i<row.length;i++){
				   			temID.push(row[i].rec_ID);
				           }
					    $.ajax({
								type:"POST",
								url:'<%=request.getContextPath() %>/system/recpoint/data_deleteRecPoint',
								data:"REC_IDS="+temID,
								async:false,
								success:function(result){
									if(result=='true'){
										alert("删除操作成功！");
										$('#tt_recpoint').datagrid('reload');
									}else{
										alert("该记录已被使用，无法删除！");
									}
								},
								error:function(result){
									alert("删除失败");
								}
					   		});
			       		}
			       	/* else{
			       		$('#tt_recpoint').datagrid('reload');
				       	} */
			    });
		   	}	
		}
		//////////////////////////////////////////////////////////////
	   
   ////////////////////////////////////////////////////////////////////////////
   //编辑返款信息
   function formatter_editretmoneyinfovo(value,obj,index)
   {
	   return "<a class='editcls' href='javascript:void(0)' onclick='edit_retmoneyinfovo_fun("+index+")'>编辑</a>";
   }
   function edit_retmoneyinfovo_fun(index){ 
	   
	    $('#tt_retmoneyinfovo').datagrid('selectRow',index);// 关键在这里  
	    var row = $('#tt_retmoneyinfovo').datagrid('getSelected'); 
	    var selRow = $('#tt_recpoint').datagrid('getSelected');   
	    if (row){ 
	        $('#dlg_edit_retmoneyinfovo').dialog('open').dialog('setTitle','编辑返款信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });  
	        $('#fm_edit_retmoneyinfovo').form('load',row);
	        $("#rec_NAME").textbox('disable',true)//设置只读 
	        $('#edit_retMoney_RecNumber').val(selRow.rec_NUMBER);   //给id为edit_retMoney_RecNumber的文本框赋值
	    }  
	}
   function save_edit_retmoneyinfovo(){
	   $('#fm_edit_retmoneyinfovo').form('submit',{
			url: '<%=request.getContextPath() %>/system/retmoneyinfovo/data_editRetMoneyInfoVo',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				var data = eval('(' + result + ')'); 
				if(data.Status){
						$.messager.alert("操作提示", "返款信息修改成功!");
						$('#dlg_edit_retmoneyinfovo').dialog('close');		// close the dialog
						$('#tt_retmoneyinfovo').datagrid('reload');	// reload the Source data
				}else{
					$.messager.alert("操作提示","<font color='red'>无法添加！</font><br/><br/>错误日志：<br/>"
							+data.Msg);
				}
				
			}
		});

	}
   
 	//刷新从表页面
   $(function(){	
	  

	$('#tt_retmoneyinfovo').datagrid({	
		onLoadSuccess:function(data){
			$('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
			
			 
	     }
	});
   })

  
 	//新增返款信息
  function add_retmoneyinfovo(){
	  var selRow = $('#tt_recpoint').datagrid('getSelected');   
	  if(selRow==null){
		  $.messager.alert("操作提示", "请选择主表记录！");
	  }else{
		  $('#dlg_add_retmoneyinfovo').dialog('open').dialog('setTitle','添加返款信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });
			$('#fm_add_retmoneyinfovo').form('clear');
			$('#RET_TIMES').combobox('setValue', '第1次返款');
			$('#add_retMoney_recName').textbox('setValue',selRow.rec_NAME);  //给id为add_retMoney_recName的文本框赋值
			$("#add_retMoney_recName").textbox('disable',true)//设置只读 
			
			
			$('#add_retMoney_recID').val(selRow.rec_ID);   //给id为add_retMoney_recID的文本框赋值
			
			$('#add_retMoney_RecNumber').val(selRow.rec_NUMBER);   //给id为add_retMoney_recNumber的文本框赋值
	  }
   }
 	//保存新增的返款信息
  function save_add_retmoneyinfovo(){
		$('#fm_add_retmoneyinfovo').form('submit',{
			url: '<%=request.getContextPath() %>/system/retmoneyinfovo/data_addRetMoneyInfoVo',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				var data = eval('(' + result + ')'); 
				if(data.Status)
				{
					$.messager.alert("操作提示", "新增返款信息保存成功！");
					$('#dlg_add_retmoneyinfovo').dialog('close');		// close the dialog
					$('#tt_retmoneyinfovo').datagrid('reload');			// reload the Source data
				
				}else
					$.messager.alert("操作提示","<font color='red'>无法添加！</font><br/><br/>错误日志：<br/>"
							+data.Msg);
			}
		});
	}
  //删除返款信息
  function delete_retmoneyinfovo(){
	  var row = $('#tt_retmoneyinfovo').datagrid('getSelections');
		var temID = [];
	   	if(row.length==0){
	   		$.messager,alert("请选择要删除的行");
	   		return;
	   	}
	   	else{
	       	//批量获取选中行的ID
	       	$.messager.confirm('提示','删除为不可逆操作,您确定要进行删除操作吗?',function(r){
		       	if(r){
		       		for(i=0;i<row.length;i++){
			   			temID.push(row[i].ret_ID);
			           }
				    $.ajax({
							type:"POST",
							url:'<%=request.getContextPath() %>/system/retmoneyinfovo/data_deleteRetMoneyInfoVo',
							data:"RET_IDS="+temID,
							async:false,
							success:function(result){
								if(result=='true'){
									alert("删除操作成功！");
									$('#tt_retmoneyinfovo').datagrid('reload');
								}else{
									alert("该记录已被使用，无法删除！");
								}
								
							},
							error:function(result){
								alert("删除失败");
							}
				   		});
		       		}
		       	/* else{
		       		$('#tt_retmoneyinfovo').datagrid('reload');
			       	} */
		    });
	   	}		
   	}
   function get_recpointid(){

	    var row = $('#tt_recpoint').datagrid('getSelections');  
	    if(row.length==0){
	   		$.messager,alert("请选择要获取ID的行");
	   		return;
	    }
        $('#dlg_getrecpointid').dialog('open').dialog('setTitle','获取招生点ID').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });  
        $('#fm_getrecpointid').form('load',row[0]); 
   }
  
  </script>
   
  </head>
  
  <body>
  
   <!-- 父列表开始 -->
   <div id="tb_recpoint" style="padding:3px"> 
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_recpoint()" >添加</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_recpoint()" >删除</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="get_recpointid()" >获取ID</a>
	  	<!-- <span>名称:</span>
	  	<input  class="easyui-textbox" id="REC_NAME" style="line-height:22px;border:1px solid #ccc"/>
	  	<span>年份:</span>
	  	<input class="easyui-textbox"  id="REC_YEAR" style="line-height:22px;border:1px solid #ccc"/> -->
	  	<span>招生点:</span>
	  	<input  class="easyui-combobox" name="FIND_REC_NAME" id="FIND_REC_NAME" style="line-height:22px;border:1px solid #ccc;width:120px;"
	  	data-options="
                url:'<%=request.getContextPath() %>/system/recpoint/data_getRecNameComboxList',
                method:'post',
                editable:false,
                valueField:'rec_NAME',
                textField:'rec_NAME',
                multiple:false,
                panelHeight:'auto'"/>
	  	
	  	<span>入学时间:</span>
	  	<input  class="easyui-combobox" name="FIND_REC_YEAR" id="FIND_REC_YEAR" style="line-height:22px;border:1px solid #ccc;width:120px;"
	  	data-options="
                url:'<%=request.getContextPath() %>/system/recpoint/data_getAdmTimeFindComboxList',
                method:'post',
                editable:false,
                valueField:'rec_YEAR',
                textField:'rec_YEAR',
                multiple:false,
                panelHeight:'auto'"/>
	  	
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch_recpoint()">查询</a>
    </div>
  <table id="tt_recpoint"  class="easyui-datagrid" rownumbers="true" title="招生点信息"  height="350px" width="fit" fitColumns ="true" toolbar = "#tb_recpoint" 
			singleSelect="true" url="system/recpoint/data_getRecPointList" pagination="true" data-options="pageSize: 15,pageList: [15,20,30]">
		<thead>
			<tr>
			
			<th field="rec_ID" width="100" align="center" checkbox="true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th  width="150" align="center" field="opr" formatter="formatter_editrecpoint">操作</th>
			<!-- <th field="rec_CODE" width="150" align="center" sortable="false">招生点编码</th> -->
			<th field="rec_NAME" width="150" align="center" >招生点</th>
			<th field="rec_YEAR" width="250" sortable="false" align="center" >入学时间</th>
			<th field="rec_NUMBER" width="250" sortable="true" align="center" >实际入学人数（人）</th>
			<th field="rec_SHOUNIT_PRICE" width="250" sortable="true" align="center" >应交费用（元/人）</th>
			<th field="remark" width="250" sortable="false" align="center">备注</th>
			
			</tr>
		</thead>
	</table>
	<!-- 父列表结束 -->
	
	<!-- 父列表弹框开始-->
	<!--父列表新增弹出框 -->
	<div id="dlg_addrecpoint" class="easyui-dialog" style="width:400px;height:320px;padding:10px 30px"
		closed="true" buttons="#dlg_buttons_addrecpoint" >
		<form id="fm_addrecpoint" method="post">
			<table cellpadding="3px">			
	        	<tr>
					<td><input name="REC_ID" type="hidden"></input></td>
				</tr>
				<tr>
					<td>招生点:</td>
					<td><input class="easyui-textbox" name="REC_NAME" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr>
				<!-- <tr>
					<td>编码:</td>
					<td><input class="easyui-textbox"  name="REC_CODE" style="width:180px" type="text" ></input></td>
				</tr> -->
				<tr>
					<td>入学时间:</td>
					<!-- <td><input class="easyui-datebox" name="FIR_PAYBACK_TIME"   value="new date()" data-options="formatter:myformatter,parser:myparser" editable="false"></input></td> -->
					<!-- <td><input  id="date" style="width:180px"></input></td> -->
					<td><input class="easyui-textbox" name="REC_YEAR" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr> 
				<tr>
					<td>实际入学人数（人）:</td>
					<td><input class="easyui-numberbox" name="REC_NUMBER" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>应缴费用（元/人）:</td>
					<td><input class="easyui-numberbox" name="REC_SHOUNIT_PRICE" id="REC_SHOUNIT_PRICE" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr>  
				<tr>
					<td>备注:</td>
					<td><input class="easyui-textbox" name="REMARK" type="text" data-options="multiline:true" style="width:180px;height:60px"></input></td>
				</tr> 
			</table>
		</form>
	</div>
	<div id="dlg_buttons_addrecpoint">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="save_addrecpoint()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_addrecpoint').dialog('close')">取消</a>
	</div>
	
	
	<!--父列表编辑弹出框  -->
	<div id="dlg_editrecpoint"  class="easyui-dialog" style="width:400px;height:320px;padding:10px 30px"
		closed="true" buttons="#dlg-buttons-editrecpoint" >
		<form id="fm_editrecpoint" method="post">
			<table cellpadding="3px">			
	        	<tr>
					<td><input name="rec_ID" type="hidden"></input></td>
				</tr>
				<tr>
					<td>名称:</td>
					<td><input class="easyui-textbox"  name="rec_NAME"  style="width:180px" type="text" data-options="required:true"></input></td>
				</tr>
				<!-- <tr>
					<td>编码:</td>
					<td><input class="easyui-textbox" name="rec_CODE" style="width:180px" type="text" editable="false"></input></td>
				</tr> -->
				<tr>
					<td>入学时间:</td>
					<td><input class="easyui-textbox" name="rec_YEAR" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr> 
				<tr>
					<td>实际入学人数（人）:</td>
					<td><input class="easyui-numberbox"  name="rec_NUMBER" style="width:180px"  type="text" data-options="required:true"></input></td>
				</tr> 
				<tr>
					<td>应缴费用（元/人）:</td>
					<td><input class="easyui-numberbox" name="rec_SHOUNIT_PRICE" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr>  
				<tr>
					<td>备注:</td>
					<td><input class="easyui-textbox" name="remark" type="text" data-options="multiline:true" style="width:180px;height:60px"></input></td>
				</tr> 
			</table>
		</form>
	</div>
	<div id="dlg-buttons-editrecpoint">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="save_editrecpoint()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_editrecpoint').dialog('close')">取消</a>
	</div>
	
	
	<div id="dlg_getrecpointid"  class="easyui-dialog" style="width:450px;height:140px;padding:10px 30px"
		closed="true" buttons="#dlg-buttons-getrecpointid" >
		<form id="fm_getrecpointid" method="post">
			<table cellpadding="3px">			
				<tr>
					<td>招生点ID:</td>
					<td><input class="easyui-textbox"  name="rec_ID" id="tempRecId" style="width:300px" type="text"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons-getrecpointid">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_getrecpointid').dialog('close')">确定</a>
	</div>
	
	<!-- 父列表弹框结束 -->
	
<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	 -->
	<!-- 子列表开始 -->
   <div id="tb_retmoneyinfovo" style="padding:3px"> 
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_retmoneyinfovo()" >添加</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_retmoneyinfovo()" >删除</a>
    </div> 
  <table id="tt_retmoneyinfovo"  class="easyui-datagrid" rownumbers="true" title="返款信息" height="240px" width="fit" fitColumns ="true" toolbar = "#tb_retmoneyinfovo" 
			singleSelect="true" url="javascript:void(0)" pagination="true" data-options="pageSize: 5,
                pageList: [5,10,15]">
		<thead>
		<tr>
			<th field="ret_ID" width="100" align="center" checkbox="true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th  width="150" align="center" field="opr" formatter="formatter_editretmoneyinfovo">操作</th>
			<th field="rec_NAME" width="150" align="center" >招生点</th>
			<th field="ret_TIMES" width="150" align="center" >返款批次</th>
			<!-- <th field="sho_UNIT_PRICE" width="200" align="center" sortable="false">应缴金额（元/人）</th> -->
			<th field="ret_UNIT_PRICE" width="200" align="center" sortable="false">应返金额（元/人）</th>
			<th field="hav_RET" width="200" sortable="false" align="center" >应返总额（元）</th>
			<!-- <th field="had_RET" width="200" sortable="true" align="center" >已返总额（元）</th>
			<th field="tal_BANLANCE" width="250" sortable="true" align="center" >合计差额（元）</th> -->
			<th field="remark" width="250" sortable="false" align="center">备注</th>
			
			</tr>
		</thead>
	</table>
	<!-- 子列表结束 -->
	
	<!-- 子列表弹框 -->
	<!--edit retmoneyinfovo弹出框  -->
	<div id="dlg_edit_retmoneyinfovo"  class="easyui-dialog" style="width:400px;height:270px;padding:10px 30px"
		closed="true" buttons="#dlg-buttons-editretmoneyinfovo" >
		<form id="fm_edit_retmoneyinfovo" method="post">
			<table cellpadding="3px">			
	        	<tr>
					<td><input name="ret_ID" type="hidden"></input></td>
					<td><input name="rec_NUMBER" id="edit_retMoney_RecNumber" type="hidden"></input></td>   <!--招生点人数 -->
				</tr>
				<tr>
					<td>名称:</td>
					<td><input class="easyui-textbox"  name="rec_NAME" style="width:180px" type="text" editable="false"></input></td>
				</tr>
				<tr>
					<td>返款批次:</td>
					<td>
						<select class="easyui-combobox" name="ret_TIMES" style="width:180px;" panelHeight="50" data-options="required:true" editable="false">
							<option value="第1次返款">第1次返款</option>
        					<option value="第2次返款">第2次返款</option>
						</select>
					</td>
				</tr>
				<!-- <tr>
					<td>应缴金额（元/人）:</td>
					<td><input class="easyui-numberbox" name="sho_UNIT_PRICE" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr> -->
				<tr>
					<td>应返总额（元/人）:</td>
					<td><input class="easyui-numberbox" name="ret_UNIT_PRICE" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr>
				<!-- <tr>
					<td>应返总额（元）:</td>
					<td><input class="easyui-textbox" name="hav_RET" style="width:180px" type="text"></input></td>
				</tr> 
				<tr>
					<td>已返总额(元):</td>
					<td><input class="easyui-textbox"  name="had_RET" style="width:180px"  type="text"></input></td>
				</tr> 
				<tr>
					<td>合计差额(元):</td>
					<td><input class="easyui-textbox"  name="tal_BANLANCE" style="width:180px"  type="text"></input></td>
				</tr>  -->
				<tr>
					<td>备注:</td>
					<td><input class="easyui-textbox" name="remark" type="text" data-options="multiline:true" style="width:180px;height:60px"></input></td>
				</tr> 
				
			</table>
		</form>
	</div>
	<div id="dlg-buttons-editretmoneyinfovo">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="save_edit_retmoneyinfovo()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_edit_retmoneyinfovo').dialog('close')">取消</a>
	</div>


	<!--add retmoneyinfovo弹出框 -->
	<div id="dlg_add_retmoneyinfovo" class="easyui-dialog" style="width:400px;height:270px;padding:10px 30px"
		closed="true" buttons="#dlg_buttons_addretmoneyinfovo" >
		<form id="fm_add_retmoneyinfovo" method="post">
			<table cellpadding="3px">			
	        	<tr>
					<td><input name="REC_ID" id="add_retMoney_recID" type="hidden"></input></td>   <!-- 招生点id -->
					<td><input name="REC_NUMBER" id="add_retMoney_RecNumber" type="hidden"></input></td>   <!--招生点人数 -->
				</tr>
				<tr>
					<td>招生点:</td>
					<td><input class="easyui-textbox"  id="add_retMoney_recName" name="add_retMoney_recName" style="width:180px;padding:0px;" type="text" editable="false"></input></td>
				</tr>
				<tr>
					<td>返款批次:</td>
					<td>
						<select class="easyui-combobox" name="RET_TIMES" id="RET_TIMES" style="width:180px;" panelHeight="50" data-options="required:true" editable="false">
							<option value="第1次返款">第1次返款</option>
        					<option value="第2次返款">第2次返款</option>
						</select>
					</td>
				</tr>
				<!-- <tr>
					<td>应缴金额（元/人）:</td>
					<td><input class="easyui-numberbox" name="SHO_UNIT_PRICE" style="width:180px" type="text"  data-options="required:true"></input></td>
				</tr> -->
				<tr>
					<td>应返总额（元/人）:</td>
					<td><input class="easyui-numberbox" name="RET_UNIT_PRICE" style="width:180px" type="text" data-options="required:true"></input></td>
				</tr>
				<!-- <tr>
					<td>应返总额(元):</td>
					<td><input class="easyui-textbox" name="HAV_RET" style="width:180px" type="text"></input></td>
				</tr> 
				<tr>
					<td>已返总额(元):</td>
					<td><input class="easyui-textbox"  name="HAD_RET" style="width:180px"  type="text"></input></td>
				</tr> 
				<tr>
					<td>合计差额(元):</td>
					<td><input class="easyui-textbox"  name="TAL_BANLANCE" style="width:180px"  type="text"></input></td>
				</tr>  -->
				<tr>
					<td>备注:</td>
					<td><input class="easyui-textbox" name="REMARK" type="text" data-options="multiline:true" style="width:180px;height:60px"></input></td>
				</tr> 
			</table>
		</form>
	</div>
	<div id="dlg_buttons_addretmoneyinfovo">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="save_add_retmoneyinfovo()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_add_retmoneyinfovo').dialog('close')">取消</a>
	</div>
	<!-- 子列表弹框 -->
  </body>
</html>
