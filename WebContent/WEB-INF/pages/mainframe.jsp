<%@ page language="java"
	import="authority.edu.csu.model.BS_ADMINUSERINFO,net.sf.json.JSONObject"
	
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在职研究生缴费信息管理系统</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/ztree/js/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/easyuicji/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ztree/js/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ztree/js/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ztree/js/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
<link href="<%=request.getContextPath()%>/js/easyuicji/css/default.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/easyuicji/js/themes/metro/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/easyuicji/js/themes/icon.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/easyuicji/js/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/easyuicji/js/outlook2.js"></script>
<script type="text/javascript">
     var _baseurl = "<%=request.getContextPath()%>/";
     <% JSONObject menu = (JSONObject)request.getAttribute("nav");
     	if(menu.getString("menus").length()<12)
     	{
     		out.print("alert('对不起，没有您的菜单信息，请联系管理员！')");
     	}
     %>
	 var _menus=<%=menu%>;
/* 	 {"menus":[{"menus":"","icon":"icon-sys","menu_NAME":"权限体系管理","menu_CODE":"m001"},{"menus":"","icon":"icon-sys","menu_NAME":"订单管理","menu_CODE":"m002"},{"menus":"","icon":"icon-sys","menu_NAME":"用户管理","menu_CODE":"m004"},{"menus":"","icon":"icon-sys","menu_NAME":"车站管理","menu_CODE":"m005"},{"menus":"","icon":"icon-sys","menu_NAME":"司机管理","menu_CODE":"m006"},{"menus":"","icon":"icon-sys","menu_NAME":"消息管理","menu_CODE":"m007"},{"menus":"","icon":"icon-sys","menu_NAME":"汽车时刻表管理","menu_CODE":"m008"}]};
 */        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 210,
                resizable:false
            }).window('open');
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }

        

        //修改密码
        function serverLogin() {
            var $oldpass = $('#txtOldPass');
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('<%=basePath %>/system/adminuserinfo/data_editPassWord?oldPW=' + $oldpass.val()+'&newPW='+$newpass.val(), function(result) {
            	if(result.Status){
            		 msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + $newpass.val(), 'info');
                     $newpass.val('');
                     $oldpass.val('');
                     $rePass.val('');
                     closePwd();
                     location.href = '<%=request.getContextPath()%>/pub/login/page_getloginout';
            	}else
            		{
            		 msgShow('系统提示', '您的原始密码不对', 'info');
            		 $oldpass.val('');
            		}
               
            })
            
        }
        
   
	$(function(){
			$('#btnCancel').click(function(){closePwd();})
			$('#btnEp').click(function(){serverLogin();})
			$('#editpass').click(function(){
				openPwd();
			});
            //closePwd();
            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = '<%=request.getContextPath()%>/pub/login/page_getloginout';
                    }
                });
            })
        });
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px;
        background: url(<%=request.getContextPath()%>/easyuicji/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
		<span style="float: right; padding-right: 20px;" class="head">欢迎
			<%
			BS_ADMINUSERINFO adminuser = (BS_ADMINUSERINFO) request
					.getSession().getAttribute("adminuser");
		%> <%=adminuser.getUSER_LOGIN_NAME()%> <a href="#" id="editpass"
			style="margin-left: 10px;">修改密码</a> <a href="javascript:void(0)"
			id="loginOut" style="margin-left: 10px;">安全退出</a>
		</span> <span style="padding-left: 10px; font-size: 16px;"><img
			src="<%=request.getContextPath()%>/js/easyuicji/images/blocks.gif"
			width="20" height="20" align="absmiddle" />在职研究生缴费信息管理系统</span>
	</div>
	<div region="south" split="true"
		style="height: 30px; background: #D2E0F2;">
		<div class="footer">在职研究生缴费信息管理系统，建议在 Firefox,Chrome,IE9/10/11,360极速模式下使用</div>
	</div>
	<div region="west" hide="true" split="true" title="导航菜单"
		style="width: 180px;height: 100%" id="west">
		<div id="nav" class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->

		</div>

	</div>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="首页"
				style="overflow: hidden; color: red;">
				<h1 style="font-size: 24px;">欢迎使用软件学院在职研究生缴费信息管理系统</h1>
				<!-- <iframe  src="http://www.brunp.com.cn/" width="100%" height="100%"></iframe> -->
			</div>
		</div>
	</div>


	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false"
		minimizable="false" maximizable="false" icon="icon-save" closed="true"
		style="width: 300px; height: 210px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>旧密码：</td>
						<td><input id="txtOldPass" class="easyui-textbox" type="password"  /></td>
					</tr>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" class="easyui-textbox" type="password"  /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="password"  class="easyui-textbox"/></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0)"> 确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0)">取消</a>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>

</body>
</html>