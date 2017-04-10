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
    <title>用户管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
		var $dg;
		var $temp;
		var $grid;
		$(function(){
			$dg=$("#dg");
			$grid=$dg.datagrid({
				url:"user/userAction!findAllUserList.action",
				width:'auto',
				height:$(this).height()-90,
				pagination:true,
				rownumbers:true,
				border:false,
				singleSelect:true,
				striped:true,
				columns:[[
					{field:'myid',title:'用户编码',width:parseInt($(this).width()*0.1),align:'left',editor:'text'},
					{field:'account',title:'用户账号',width:parseInt($(this).width()*0.1),align:'left',editor:'text'},
					{field:'name',title:'用户名',width:parseInt($(this).width()*0.1),editor:{type:'validatebox',options:{required:true}}},
					{field:'password',title:'用户密码',width:parseInt($(this).width()*0.1),editor:'validatebox'},
					{field:'email',title:'邮箱',width:parseInt($(this).width()*0.1),align:'left',editor:{type:'validatebox',options:{required:true,validType:'email'}}},
					{field:'tel',title:'电话',width:parseInt($(this).width()*0.1),align:'left',editor:'text'},
					{field:'organizeId',title:'组织',width:parseInt($(this).width()*0.1),align:'left',
						formatter:function(value,row){
							return row.organizeName;
						},
						editor:{
							type:'combotree',
							options:{
								url:"orgz/organizationAction!findOrganizationList.action",
								idField:"id",
								textField:"name",
								parentField:"pid",
								onSelect:updCellTree,
								required:true
							}
						}
					},
					{hidden:true,field:'organizeName',title:'组织部门',width:parseInt($(this).width()*0.1),align:'left',editor:'text'},
					{field:'description',title:'描述',width:parseInt($(this).width()*0.1),align:'left',editor:'text'}
				]],
				toolbar:'#tb'
			});
			
			$("#searchbox").searchbox({
				menu:"#mm",
				prompt:"模糊查询",
				searcher:function(value,name){
					var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
					var obj=eval('('+str+')');
					$dg.datagrid('reload',obj);
				}
			});
		});
		
		function updCellTree(record){
				var rowIdx = $dg.datagrid('getRowIndex',$dg.datagrid('getSelected'));
				 $dg.datagrid('getEditor',{'index':rowIdx,'field':'organizeName'}).target.val(record.text);

			}
		
		function addRowsOpenDlg(){
			parent.$.modalDialog({
				title:'添加用户',
				width:600,
				height:400,
				href:'jsp/user/userEditDlg.jsp',
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
		
		function userSearch(){
			jqueryUtil.gradeSearch($dg,"#userSearchFm","jsp/user/userSearchDlg.jsp");
		}
		
		function userSearchRemove(curr){
			$(curr).closest('tr').remove();
		}
		
		function updRowsOpenDlg(){
			var row=$dg.datagrid('getSelected');
			if(row){
				parent.$.modalDialog({
					title:"编辑用户",
					width:600,
					height:400,
					href:"jsp/user/userEditDlg.jsp",
					onLoad:function(){
						var f=parent.$.modalDialog.handler.find("#form");
						f.form("load",row);
					},
					buttons:[{
						text:"编辑",
						iconCls : 'icon-ok',
						handler:function(){
							parent.$.modalDialog.openner=$grid;
							var f=parent.$.modalDialog.handler.find("#form");
							f.submit();
						}
					},{
						text:'取消',
						iconCls : 'icon-cancel',
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
		
		function delRows(){
			var row=$dg.datagrid('getSelected');
			if(row){
				parent.$.messager.confirm("提示","确定要删除记录吗",function(r){
					if(r){
						var rowIndex=$dg.datagrid('getRowIndex',row);
						$dg.datagrid('deleteRow',rowIndex);
						$.ajax({
							url:"user/userAction!delUsers.action",
							data:"userId="+row.userId,
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
					msg : "请选择行数据!",
					timeout : 1000 * 2
				});
			}
		}
	</script>
  </head>
  <body>
  <div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>用户</strong></span>进行编辑!
				</p>
			</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="userAdd">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="userEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="userDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
						</shiro:hasPermission>
					</td>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="userSearch();">高级查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm">
				<div name="myid">用户编码</div>
				<div name="account">用户账户</div>
				<div name="name">用户名</div>
				<div name="email">邮箱</div>
				<div name="tel">电话</div>
				<div name="organizeName">组织</div>
				<div name="description">描述</div>
		</div>
  		<table id="dg" title="用户管理"></table>
  </body>
</html>