<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>日志管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
		var $dg;
		var $grid;
		$(function(){
			$dg=$("#dg");
			$grid=$dg.datagrid({
				url:"logs/logsAction!findLogsAllList.action",
				width:'auto',
				height:$(this).height()-85,
				pagination:true,
				rownumbers:true,
				border:true,
				striped:true,
				singleSelect:true,
				columns:[[
							{field : 'name',title : '操作用户',width : parseInt($(this).width()*0.1)},
							{field : 'ip',title : 'IP地址',width : parseInt($(this).width()*0.1)},
							{field : 'mac',title : '物理地址',width : parseInt($(this).width()*0.1)},
							{field : 'logDate',title : '日志日期',width : parseInt($(this).width()*0.1)},
							{field : 'type',title : '日志类型',width : parseInt($(this).width()*0.1),align : 'left',formatter:function(value,row){
								  if("1"==row.type)
										return "<font color=green>安全日志<font>";
									  else
										return "<font color=red>操作日志<font>";  
								}},
							{field : 'eventName',title : '操作名称',width :parseInt($(this).width()*0.1),align : 'left'},
							{field : 'objectId',title : '模型ID',width :parseInt($(this).width()*0.1),align : 'left'},
							{field : 'eventRecord',title : '操作描述',width : parseInt($(this).width()*0.2),align : 'left'}
				          ]],
				toolbar:"#tb"
			});
			
			$("#searchbox").searchbox({
				menu:"#mm",
				prompt:'模糊查询',
				searcher:function(value,name){
					if(name=="type"){
						if("安全".indexOf(value)>=0){
							value=1;
						}
						else if("操作".indexOf(value)>=0){
							value=2;
						}
						else{
							value="";
						}
					}
					var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
					var obj=eval('('+str+')');
					$dg.datagrid("reload",obj);
				}
			});
		});
		
		function delRows(){
			var row=$dg.datagrid("getSelected");
			if(row){
				parent.$.messager.confirm("提示","确定要删除记录吗",function(r){
					if(r){
						var rowIndex=$dg.datagrid('getRowIndex',row);
						$dg.datagrid("deleteRow",rowIndex);
						$.ajax({
							url:"logs/logsAction!delLogs.action",
							data:"logId="+row.logId,
							success:function(rsp){
								parent.$.messager.show({
									title:rsp.title,
									msg:rsp.message,
									timeout:1000*2
								});
							}
						});
					}
				});
			}else{
				parent.$.messager.show({
					title : "提示",
					msg :"请选择一行记录!",
					timeout : 1000 * 2
				});
			}
		}
		
		function updRowsOpenDlg(){
			var row=$dg.datagrid('getSelected');
			if(row){
				parent.$.modalDialog({
					title:'编辑日志',
					width:600,
					height:400,
					href:"jsp/logs/logsEditDlg.jsp",
					onLoad:function(){
						var f=parent.$.modalDialog.handler.find("#form");
						f.form("load",row);
					},
					buttons:[{
						text:'编辑',
						iconCls:'icon-ok',
						handler:function(){
							parent.$.modalDialog.openner=$grid;
							var f=parent.$.modalDialog.handler.find("#form");
							f.submit();
						}
					},{
						text:'取消',
						iconCls:'icon-cancle',
						handler:function(){
							parent.$.modalDialog.handler.dialog('destroy');
							parent.$.modalDialog.handler=undefined;
						}
					}]
				});
			}else{
				parent.$.messager.show({
					title:"提示",
					msg:"请选择一行记录",
					timeout:1000*2
				});
			}
		}
		
		function addRowsOpenDlg(){
			parent.$.modalDialog({
				title:'添加日志',
				width:600,
				height:400,
				href:"jsp/logs/logsEditDlg.jsp",
				buttons:[{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						parent.$.modalDialog.openner=$grid;
						var f=parent.$.modalDialog.handler.find("#form");
						f.submit();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						parent.$.modalDialog.handler.dialog('destroy');
						parent.$.modalDialog.handler=undefined;
					}
				}]
			});
		}
	</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>日志</strong></span>进行编辑!
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="logAdd">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="logEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="logDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
						</shiro:hasPermission>
					</td>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<!--  <td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tbsCompanySearch();">高级查询</a>
					</td>-->
				</tr>
			</table>
		</div>
		<div id="mm">
				<div name="name">操作用户</div>
				<div name="type">类型</div>
				<div name="eventName">操作名称</div>
				<div name="eventRecord">操作描述</div>
		</div>
		<table id="dg" title="日志管理"></table>
  	</div>	
  </body>
</html>
