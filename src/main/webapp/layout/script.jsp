<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
		String easyuiThemeName="metro";
		Cookie cookies[] =request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for(Cookie cookie : cookies){
				if (cookie.getName().equals("cookiesColor")) {
					easyuiThemeName = cookie.getValue();
					break;
				}
			}
		}
	%>
	<link rel="stylesheet" type="text/css" href="themes/<%=easyuiThemeName %>/easyui.css">
	<script type="text/javascript" src="js/xheditor/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/xheditor/xheditor-1.1.14-zh-cn.min.js" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jqueryUtil.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/FusionCharts/FusionCharts.js"></script>
	<script type="text/javascript" src="js/FusionCharts/FusionCharts.jqueryplugin.js"></script>
	<script type="text/javascript" src="js/FusionCharts/FusionChartsExportComponent.js"></script>
	<style type="text/css">
		body {
		    font-family:helvetica,tahoma,verdana,sans-serif;
		    font-size:13px;
		    margin:0px 0px 0px 0px;
		    padding:0px 0px 0px 0px;
		}
	</style>
	