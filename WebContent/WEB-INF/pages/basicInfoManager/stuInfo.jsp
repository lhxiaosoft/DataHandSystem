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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <base href="<%=basePath%>">
    <title>学生信息</title>
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/js/easyui/themes/	metro/easyui.css">
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
	  $('#tt_stuinfo').datagrid({
		  onClickRow: function(index, row){
			  $('#tt_retmoneyinfovo').datagrid('load',{
				  	REC_ID:row.rec_ID
				});
		  }
	  });
  })
    
    
    //////////////////////////////////////////////////////////
	//编辑信息
   function formatter_editstuinfo(value,obj,index)
   {
	   return "<a class='editcls' href='javascript:void(0)' onclick='editstuinfo_fun("+index+")'>编辑</a>";
   }
   function editstuinfo_fun(index){ 
	    $('#tt_stuinfo').datagrid('selectRow',index);// 关键在这里  
	    var row = $('#tt_stuinfo').datagrid('getSelected');  
	    if (row){ 
	        $('#dlg_editstuinfo').dialog('open').dialog('setTitle','编辑学生信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });  
	        $('#fm_editstuinfo').form('load',row); 
	        
	        //var row = $('#tt_stuinfo').datagrid('getSelections');
	        $('#editStuInfoAdmTime').combobox('setValue', '');
	        $('#editStuInfoRecId').combobox('setValue', '');
	        //$('#editStuInfoRecId').combobox('setValue', row[0].recName);   //rec_ID
	      	//下拉列表
			$('#editStuInfoAdmTime').combobox({
				url:'system/recpoint/data_getAdmTimeComboxList',
				valueField:'rec_YEAR',
				textField:'rec_YEAR',
				panelHeight:'auto',
				onSelect:function(rec){
					var selAdmTime=$('#editStuInfoAdmTime').combobox('getValue');   //获取combobox中选中的值
					$('#editStuInfoRecId').combobox({
						url:encodeURI('system/recpoint/data_getRecIdComboxList?REC_YEAR='+selAdmTime),
						valueField:'rec_ID',
						textField:'rec_NAME',
						panelHeight:'auto',
						editable:false
					});	
											
				}
			});
	    }  
	}
	   function save_editstuinfo(){
			$('#fm_editstuinfo').form('submit',{
				url: '<%=request.getContextPath() %>/system/stuinfo/data_editstuinfo',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='true')
					{
							$.messager.alert("操作提示", "学生信息修改成功!");
							$('#dlg_editstuinfo').dialog('close');		// close the dialog
							$('#tt_stuinfo').datagrid('reload');	// reload the Source data
					}else
						$.messager.alert("操作提示", "保存失败！");
					
				}
			});
		}
		//////////////////////////////////////////////////////////
		
		
	   //刷新主列表页面
	   $(function(){
		$('#tt_stuinfo').datagrid({	
			onLoadSuccess:function(data){  
				$('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
		     }
		});
	   })
	   //搜索
		function doSearch_stuinfo(){
			$('#tt_stuinfo').datagrid('load',{
				ADM_TIME: $('#FIND_REC_YEAR').combobox('getValue'),
				recName: $('#FIND_REC_NAME').combobox('getValue'),
				GRA_STATE: $('#FIND_GRA_STATE').combobox('getValue'),
				LESSPAY_STATE: $('#FIND_LESSPAY_STATE').combobox('getValue'),
				STU_NAME: $('#FIND_STU_NAME').val()
			});
	   }

	   //////////////////////////////////////////////////////////////
	   /* //新增学生信息
	   function add_stuinfo(){
		   $('#dlg_addstuinfo').dialog('open').dialog('setTitle','新增学生信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });
			$('#dlg_addstuinfo').form('clear');
		   } */
	   function save_addstuinfo(){
			$('#fm_addstuinfo').form('submit',{
				url: '<%=request.getContextPath() %>/system/stuinfo/data_addstuinfo',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					if(result=='true')
					{
						$.messager.alert("操作提示", "学生信息新增成功！");
						$('#dlg_addstuinfo').dialog('close');		// close the dialog
						$('#tt_stuinfo').datagrid('reload');			// reload the Source data
					
					}else
						$.messager.alert("操作提示", "保存失败！");
				}
			});
		}
	   function add_stuinfo(){
		   $('#dlg_addstuinfo').dialog('open').dialog('setTitle','新增学生信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });
			$('#dlg_addstuinfo').form('clear');
			$('#GRA_STATE').combobox('setValue', '在读');
			$('#STU_SEX').combobox('setValue', '男'); 
			$('#SHOULD_PAY').textbox('setValue', '36000');
			//下拉列表
			$('#addStuInfoAdmTime').combobox({
				url:'system/recpoint/data_getAdmTimeComboxList',
				valueField:'rec_YEAR',
				textField:'rec_YEAR',
				panelHeight:'auto',
				onSelect:function(rec){
					var selAdmTime=$('#addStuInfoAdmTime').combobox('getValue');   //获取combobox中选中的值
					$('#addStuInfoRecId').combobox({
						url:encodeURI('system/recpoint/data_getRecIdComboxList?REC_YEAR='+selAdmTime),
						valueField:'rec_ID',
						textField:'rec_NAME',
						panelHeight:'auto',
						editable:false
					});	
											
				}
			});
			
	   }
	   
		//////////////////////////////////////////////////////////////
	   
	   //////////////////////////////////////////////////////////////
	   //删除学生信息
	   function delete_stuinfo(){
			var row = $('#tt_stuinfo').datagrid('getSelections');
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
				   			temID.push(row[i].stu_INFO_ID);
				           }
					    $.ajax({
								type:"POST",
								url:'<%=request.getContextPath() %>/system/stuinfo/data_deletestuinfo',
								data:"STU_INFO_IDS="+temID,
								async:false,
								success:function(result){
									if(result=='true'){
										alert("删除操作成功！");
										$('#tt_stuinfo').datagrid('reload');
									}else{
										alert("该记录已被使用，无法删除！");
									}
									
								},
								error:function(result){
									alert("删除失败");
								}
					   		});
			       		}
			       	else{
			       		$('#tt_stuinfo').datagrid('reload');
				       	}
			    });
		   	}	
		}
		//////////////////////////////////////////////////////////////
	   //格式化日期
		function myformatter(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
					+ (d < 10 ? ('0' + d) : d);
		}
		function myparser(s) {
			if (!s)
				return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0], 10);
			var m = parseInt(ss[1], 10);
			var d = parseInt(ss[2], 10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
				return new Date(y, m - 1, d);
			} else {
				return new Date();
			}
		}
		
		
		//导入学生文件
	   function import_stuinfo(){
		   $('#dlg_importstuinfo').dialog('open').dialog('setTitle','导入学生信息').dialog('move',{top:$(document).scrollTop()+($(window).height() - 370) * 0.5 });
			$('#dlg_importstuinfo').form('clear');
		   }
	   function save_importstuinfo(){
		 	//得到导入文件的文件名  
	         var fileName= $('#excelFile').filebox('getValue');  
	       	//进行基本校验  
             if(fileName==""){     
                $.messager.alert('提示','请选择上传文件！','info');   
             }else{
            	
            	//对文件格式进行校验 
            	 var d1=/\.[^\.]+$/.exec(fileName);   
                 if(d1==".xls" || d1==".xlsx"){
                	 $.messager.progress({ 
              		    text: '正在导入.......' 
              			}); 
                	 $('#fm_importstuinfo').form('submit',{
          				url: '<%=request.getContextPath() %>/system/stuinfo/data_importstuinfo',
          				onSubmit: function(){
          					return $(this).form('validate');
          				},
          				success: function(result){
          					//var data = eval('(' + result + ')');
          					if(result=='true')
          					{
          						$.messager.alert("操作提示", "学生信息导入成功！");
          						$('#dlg_importstuinfo').dialog('close');		// close the dialog
          						$('#tt_stuinfo').datagrid('reload');			// reload the Source data
          					
          					}else
          						
          						$.messager.alert("操作提示", "导入的文件属性列错误，学生信息导入失败！");
          						
          				 $.messager.progress('close');
          				}
          			});
                 }else{
                	 $.messager.alert('提示','请选择.xls或.xlsx格式文件！','info');  
                 }
                
             }
			
		}
	   
	 	//导出学生文件
	   function output_stuinfo(){
		   	var ADM_TIME=$('#FIND_REC_YEAR').combobox('getValue');   
			var recName=$('#FIND_REC_NAME').combobox('getValue');
			var GRA_STATE=$('#FIND_GRA_STATE').combobox('getValue');
			var LESSPAY_STATE=$('#FIND_LESSPAY_STATE').combobox('getValue');
			var STU_NAME=$('#FIND_STU_NAME').val();
			var elemIF = document.createElement("iframe");   
            elemIF.src = encodeURI('<%=request.getContextPath() %>/system/stuinfo/data_outputstuinfo?ADM_TIME='+ADM_TIME+'&recName='+recName+'&GRA_STATE='+GRA_STATE+'&LESSPAY_STATE='+LESSPAY_STATE+'&STU_NAME='+STU_NAME);   
            elemIF.style.display = "none";   
            document.body.appendChild(elemIF); 
		  
		}

	  </script>
	  
   
  </head>
  
  <body >
  
   <!-- 列表开始 -->
   <div id="tb_stuinfo" style="padding:3px"> 
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_stuinfo()" >添加</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delete_stuinfo()" >删除</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-redo"  plain="true" onclick="import_stuinfo()">导入</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-undo"  plain="true" onclick="output_stuinfo()">导出</a>
	  	
	  	<span>入学时间:</span>
	  	<input  class="easyui-combobox" name="FIND_REC_YEAR" id="FIND_REC_YEAR" style="line-height:22px;border:1px solid #ccc;width:100px;"
	  	data-options="
                url:'<%=request.getContextPath() %>/system/recpoint/data_getAdmTimeFindComboxList',
                method:'post',
                editable:false,
                valueField:'rec_YEAR',
                textField:'rec_YEAR',
                multiple:false,
                panelHeight:'auto'"/>
	  	<span>招生点:</span>
	  	<input  class="easyui-combobox" name="FIND_REC_NAME" id="FIND_REC_NAME" style="line-height:22px;border:1px solid #ccc;width:100px;"
	  	data-options="
                url:'<%=request.getContextPath() %>/system/recpoint/data_getRecNameComboxList',
                method:'post',
                editable:false,
                valueField:'rec_NAME',
                textField:'rec_NAME',
                multiple:false,
                panelHeight:'auto'"/>
         <span>毕业状态:</span>
         <input id="FIND_GRA_STATE" class="easyui-combobox" name="FIND_GRA_STATE"  style="line-height:22px;border:1px solid #ccc;width:100px;" 
         data-options="
				valueField:'label',
				textField:'value',
				data:[
				{
					label:'全部',
					value:'全部',
				},{
					label:'在读',
					value:'在读',
				},{
					label:'已毕业',
					value:'已毕业',
				}],
				editable:false,
				panelHeight:'auto'" /> 
         <span>欠费状态:</span>
         <input id="FIND_LESSPAY_STATE" class="easyui-combobox" name="FIND_LESSPAY_STATE"  style="line-height:22px;border:1px solid #ccc;width:100px;"
          data-options="
				valueField:'label',
				textField:'value',
				data:[
				{
					label:'全部',
					value:'全部',
				},{
					label:'欠费',
					value:'欠费',
				},{
					label:'未欠费',
					value:'未欠费',
				}],
				editable:false,
				panelHeight:'auto'
				" /> 
		<span>姓名:</span>
	  	<input  class="easyui-textbox" id="FIND_STU_NAME" style="line-height:22px;border:1px solid #ccc;width:100px;"/>
	  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch_stuinfo()">查询</a>
    </div>
  <table id="tt_stuinfo"  class="easyui-datagrid" rownumbers="true" title="学生信息" fit="true"  toolbar = "#tb_stuinfo" 
			singleSelect="false" url="system/stuinfo/data_getStuInfoList" pagination="true" data-options="pageSize: 20,pageList: [20,30,40]">
		<thead>
			<tr>
			
			<th field="stu_INFO_ID" width="100" align="center" checkbox="true"></th> <!-- field 不能重复。不能设置单选，否则只能选中一列 -->
			<th  width="80" align="center" field="opr" formatter="formatter_editstuinfo">操作</th>
			<th field="stu_NAME" width="80" sortable="false" align="center" >姓名</th>
			<th field="stu_CODE" width="100" align="center" sortable="false">学号</th>
			<th field="stu_SEX" width="60" sortable="false" align="center" >性别</th>
			<th field="adm_TIME" width="80" align="center" sortable="false">入学时间</th>
			<th field="recName" width="100" align="center" >招生点</th>
			<th field="rec_ID" width="100" hidden="true" >招生点id</th>
			<th field="master" width="80" sortable="false" align="center" >导师</th>
			<th field="gra_STATE" width="80" sortable="false" align="center">毕业状态</th>
			<th field="fir_PAY" width="80" align="center" sortable="false">第一年缴费</th>
			<th field="sec_PAY" width="80" align="center" sortable="false">第二年缴费</th>
			<th field="thi_PAY" width="80" align="center" sortable="false">第三年缴费</th>
			<th field="fir_PAYBACK" width="80" sortable="false" align="center" >第一次返款</th>
			<th field="fir_PAYBACK_TIME" width="100" sortable="false" align="center" >第一次返款时间</th>
			<th field="sec_PAYBACK" width="80" sortable="false" align="center" >第二次返款</th>
			<th field="sec_PAYBACK_TIME" width="100" sortable="false" align="center" >第二次返款时间</th>
			<th field="should_PAY" width="80" sortable="false" align="center" >应缴总费</th>
			<th field="all_PAY" width="80" sortable="false" align="center" >已缴费用</th>
			<th field="all_LESSPAY" width="80" sortable="true" align="center" >欠费金额</th>
			<th field="lesspay_STATE" width="80" sortable="false" align="center" >欠费状态</th>
			<th field="remark" width="100" sortable="false" align="center">备注</th>
			</tr>
		</thead>
	</table>
	<!-- 列表结束 -->
	
	<!-- 上传学生文件 -->
	<div id="dlg_importstuinfo" class="easyui-dialog" title="import File" style="width:400px;padding:30px 70px 50px 70px" closed="true">
		<form id="fm_importstuinfo"  style="width: 100%;" method="post" enctype="multipart/form-data">
	        <div style="margin-bottom:20px">
	            <input class="easyui-filebox" name="excelFile" id="excelFile" data-options="prompt:'请选择.xls或.xlsx文件',buttonText: '请选择文件...'" style="width:100%">
	        </div>
	        <div>
	            <a  class="easyui-linkbutton" iconCls="icon-ok" style="width:100%" onclick="save_importstuinfo()">确定</a>
	        </div>
	    </form>
    </div>
	
	
	
	<!--新增弹出框 -->
	<div id="dlg_addstuinfo" title="学生基本信息" class="easyui-dialog" style="width:660px;height:380px"
		closed="true" buttons="#dlg_buttons_addstuinfo" >

		<form id="fm_addstuinfo"  style="width: 100%;" method="post">
			<table cellpadding="4"  align="center"  style="width: 90%; margin-top: 10px;">			
				<tr>
					<td>学号:</td>
					<td><input class="easyui-textbox" name="STU_CODE"  type="text" ></input></td>
					<td>姓名:</td>
					<td><input class="easyui-textbox" name="STU_NAME"  type="text" data-options="required:true"></input></td>
				
				</tr>
				<tr>
					<td>性别:</td>
					<td><input class="easyui-combobox"  name="STU_SEX"  id="STU_SEX" panelHeight="50px" data-options="
							valueField:'label',
							textField:'value',
							data:[{
								label:'男',
								value:'男',
							},{
								label:'女',
								value:'女',
							}],
							editable:false,
							required:true
							"/>
					</td>
					<td>导师:</td>
					<td><input class="easyui-textbox"  name="MASTER"  type="text" ></input></td>
				</tr>
				<tr>
					<td>入学时间:</td>
					<td><input class="easyui-combobox" name="ADM_TIME" id="addStuInfoAdmTime"  data-options="required:true" editable="false"></input></td>
					<td>招生点:</td>
					<td><input class="easyui-combobox"  name="REC_ID" id="addStuInfoRecId"  data-options="required:true" editable="false"></input></td>
					
				</tr> 
				<tr>
					<td>第一年缴费:</td>
					<td><input class="easyui-numberbox" name="FIR_PAY"  type="text"></input></td>
					<td>第二年缴费:</td>
					<td><input class="easyui-numberbox" name="SEC_PAY"  type="text"></input></td>
					
				</tr> 
				<tr>
					<td>第三年缴费:</td>
					<td><input class="easyui-numberbox" name="THI_PAY"  type="text"></input></td>
					<td>应缴总费:</td>
					<td><input class="easyui-numberbox" name="SHOULD_PAY" id="SHOULD_PAY"  type="text"></input></td>
					
				</tr> 
				<tr>
					<td>第一次返款:</td>
					<td><input class="easyui-numberbox" name="FIR_PAYBACK"  type="text"></input></td>
					<td>第一次返款时间:</td>
					<td><input class="easyui-datebox" name="FIR_PAYBACK_TIME"   value="new date()" data-options="formatter:myformatter,parser:myparser" editable="false"></input></td>
				
					
				</tr>
				<tr>
					<td>第二次返款:</td>
					<td><input class="easyui-numberbox" name="SEC_PAYBACK"  type="text"></input></td>
					<td>第二次返款时间:</td>
					<td><input class="easyui-datebox" name="SEC_PAYBACK_TIME"   value="new date()" data-options="formatter:myformatter,parser:myparser" editable="false"></input></td>
				</tr> 
				<tr>
					
					<td>毕业状态:</td>
					<td>
						<input id="GRA_STATE" class="easyui-combobox" name="GRA_STATE" panelHeight="50px" data-options="
							valueField:'label',
							textField:'value',
							data:[{
								label:'在读',
								value:'在读',
							},{
								label:'已毕业',
								value:'已毕业',
							}],
							editable:false,
							required:true
							" /> 
					</td>
					<td >备注:</td>
					<td colspan="3"><input class="easyui-textbox" name="REMARK" type="text"></input></td>
				</tr> 
			</table>
		</form>
	</div>
	<div id="dlg_buttons_addstuinfo">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="save_addstuinfo()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_addstuinfo').dialog('close')">取消</a>
	</div>
	
	
	<!--编辑弹出框 -->
	<div id="dlg_editstuinfo" title="学生基本信息" class="easyui-dialog" style="width:660px;height:380px"
		closed="true" buttons="#dlg_buttons_editstuinfo" >

		<form id="fm_editstuinfo"  style="width: 100%;" method="post">
			<table cellpadding="4"  align="center"  style="width: 90%; margin-top: 10px;">	
				<tr>
					<td><input name="stu_INFO_ID"  type="hidden"></input></td>
				</tr>		
				<tr>
					<td>学号:</td>
					<td><input class="easyui-textbox" name="stu_CODE"  type="text" ></input></td>
					<td>姓名:</td>
					<td><input class="easyui-textbox" name="stu_NAME"  type="text" data-options="required:true"></input></td>
					
				
				</tr>
				<tr>
					<td>性别:</td>
					<td><input class="easyui-combobox"  name="stu_SEX"  panelHeight="50px" data-options="
							valueField:'label',
							textField:'value',
							data:[{
								label:'男',
								value:'男',
							},{
								label:'女',
								value:'女',
							}],
							editable:false,
							required:true
							"/>
					</td>
					<td>导师:</td>
					<td><input class="easyui-textbox"  name="master"  type="text" ></input></td>
				</tr>
				<tr>
					<td>入学时间:</td>
					<td><input class="easyui-combobox" name="admTIME" id="editStuInfoAdmTime"  data-options="required:true" editable="false"></input></td>
					<td>招生点:</td>
					<td><input class="easyui-combobox"  name="recID" id="editStuInfoRecId"  data-options="required:true" editable="false"></input></td>
					
				</tr> 
				<tr>
					<td>第一年缴费:</td>
					<td><input class="easyui-numberbox" name="fir_PAY"  type="text"></input></td>
					<td>第二年缴费:</td>
					<td><input class="easyui-numberbox" name="sec_PAY"  type="text"></input></td>
					
				</tr> 
				<tr>
					<td>第三年缴费:</td>
					<td><input class="easyui-numberbox" name="thi_PAY"  type="text"></input></td>
					<td>应缴总费:</td>
					<td><input class="easyui-numberbox" name="should_PAY"  type="text"></input></td>
					
				</tr> 
				<tr>
					<td>第一次返款:</td>
					<td><input class="easyui-numberbox" name="fir_PAYBACK"  type="text"></input></td>
					<td>第一次返款时间:</td>
					<td><input class="easyui-datebox" name="fir_PAYBACK_TIME"   value="new date()" data-options="formatter:myformatter,parser:myparser" editable="false"></input></td>
					
					
				</tr>
				<tr>
					<td>第二次返款:</td>
					<td><input class="easyui-numberbox" name="sec_PAYBACK"  type="text"></input></td>
					<td>第二次返款时间:</td>
					<td><input class="easyui-datebox" name="sec_PAYBACK_TIME"   value="new date()" data-options="formatter:myformatter,parser:myparser" editable="false"></input></td>
				</tr> 
				<tr>
					
					<td>毕业状态:</td>
					<td>
						<input id="gra_STATE" class="easyui-combobox" name="gra_STATE" style="height:26px;" panelHeight="50px" panelHeight="50px" data-options="
							valueField:'label',
							textField:'value',
							data:[{
								label:'在读',
								value:'在读',
							},{
								label:'已毕业',
								value:'已毕业',
							}],
							editable:false,
							required:true
							" /> 
					</td>
					<td>备注:</td>
					<td><input class="easyui-textbox" name="remark" type="text" ></input></td>
				</tr> 
				
			</table>
		</form>
	</div>
	<div id="dlg_buttons_editstuinfo">
		<a  class="easyui-linkbutton" iconCls="icon-ok" onclick="save_editstuinfo()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_editstuinfo').dialog('close')">取消</a>
	</div>
	
	
	
	
	

  </body>
</html>
