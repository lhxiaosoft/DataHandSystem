/**
 * 公共JS
 */
   function formatFlag(val,row){
    	var str1="否";
    	var str2="是";
    	if (val == undefined) {
            return "";
        }
    	if(val==0){
    		return '<span style="color:red;">'+str1+'</span>';
    	}else{
    		return '<span style="color:green;">'+str2+'</span>';
    	}
    	
        
    };

    $(function(){
    $.ajaxSetup({ 
    　　　　error: function (XMLHttpRequest, textStatus, errorThrown){
    　　　　　　if(XMLHttpRequest.status==403){
    　　　　　　　　$.messager.alert('我的消息', '您没有权限访问此资源或进行此操作！', 'success');
    　　　　　　　　return false;
    　　　　　　}
    　　　　}, 
    　　　　complete:function(XMLHttpRequest,textStatus){ 
    　　　　　　var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus， 
    　　　　　　if(sessionstatus=='timeout'){ 
    　　　　　　　　//如果超时就处理 ，指定要跳转的页面 
    　　　　　　　　var top = getTopWinow(); //获取当前页面的顶层窗口对象
    　　　　　　　　$.messager.alert('我的消息', '登录超时-请重新登录！', 'info');
    　　　　　　　　top.location.href = "http://"+getHostUrl()+"/system/"; //跳转到登陆页面 对多服务器同样适用
    　　　　　　} 
    　　　　} 
    });

    /** 
    * 在页面中任何嵌套层次的窗口中获取顶层窗口 
    * @return 当前页面的顶层窗口对象 
    */
    function getTopWinow(){ 
    　　　　　　　　var p = window; 
    　　　　　　　　while(p != p.parent){ 
    　　　　　　　　　　　　p = p.parent; 
    　　　　　　　　} 
    　　　　return p; 
    　　　}
    function getHostUrl(){
    	var hosturl=window.location.host;
    	var pathName=window.document.location.pathname; 
    	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    	return hosturl+projectName;
    }
    $.extend($.fn.validatebox.defaults.rules, {
		md: {
			validator: function(value, param){
				var origVal = $("#"+param[0]).datebox('getValue');
				var d1 = $.fn.datebox.defaults.parser(origVal);
				var d2 = $.fn.datebox.defaults.parser(value);
				return d2>=d1;
			},
			message: '{1}'
		}
	});
    });