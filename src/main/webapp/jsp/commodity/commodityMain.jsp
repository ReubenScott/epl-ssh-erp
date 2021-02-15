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
    <title>商品管理</title>
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
				url:"supermarket/commodityAction!findCommodityList.action",
				width:'auto',
				height:$(this).height()-85,
				pagination:true,
				rownumbers:true,
				border:true,
				striped:true,
				singleSelect:true,
				columns:[[
					{field : 'cargoNo',title : '货号',width : parseInt($(this).width()*0.1),align : 'left'},
					{field : 'barcode',title : '商品条码',width : parseInt($(this).width()*0.1)},
					{field : 'title',title : '商品名称',width : parseInt($(this).width()*0.1)},
					{field : 'categoryId',title : '商品类别',width :parseInt($(this).width()*0.1),align : 'left'},
					{field : 'purchasePrice',title : '进价',width : parseInt($(this).width()*0.1),align : 'left'},
					{field : 'standard',title : '规格型号',width : parseInt($(this).width()*0.1),align : 'left'},
					{field : 'unit',title : '单位',width : parseInt($(this).width()*0.1),align : 'left'},
					{field : 'purchasePrice',title : '进价',width : parseInt($(this).width()*0.1),align : 'left'},
					{field : 'standard',title : '进价',width : parseInt($(this).width()*0.1),align : 'left'},
					{field : 'salePrice',title : '零售价',width : parseInt($(this).width()*0.1),align : 'left'},
					{field : 'memberPrice',title : '会员价',width : parseInt($(this).width()*0.1),align : 'left'},
					{field:'state',title:'商品状态',width:parseInt($(this).width()*0.1),
						formatter:function(value,row){
							if("T" == row.state)
								return "<font color=green>交易中<font>";
							else
								return "<font color=red>禁用<font>";
						}
					},
					{hidden:'saleId',title:'销售',width:parseInt($(this).width()*0.1),align:'left',
						formatter:function(value,row){
							return "0"+row.saleId;
						}
					},
					{field : 'remarks',title : '描述',width : parseInt($(this).width()*0.3),align : 'left'}
				]],
				toolbar:'#tb'
			});
			
			$("#searchbox").searchbox({
				menu:"#mm",
				prompt:'模糊查询',
				searcher:function(value,name){
					var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
			        var obj = eval('('+str+')');
			        $dg.datagrid('reload',obj);
				}
			});
		});
		
		//弹框增加
		function addRowsOpenDlg(){
			parent.$.modalDialog({
				title:'添加商品',
				width:900,
				height:550,
				href:"jsp/commodity/commodityEditDlg.jsp",
				buttons:[{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						parent.$.modalDialog.openner=$grid;
						parent.$.modalDialog.handler.find("#form").submit();
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
		
		//弹框修改
		function updRowsOpenDlg(){
			var row=$dg.datagrid('getSelected');
			if(row){
				parent.$.modalDialog({
					title:'编辑商品',
					width:900,
					height:550,
					href : "jsp/commodity/commodityEditDlg.jsp?tempId="+row.customerId,
					onLoad:function(){
						var f =parent.$.modalDialog.handler.find("#form");
						row.saleId=(typeof(row.saleId)=="undefined")?row.saleId:"0"+row.saleId;
						row.cityId=(typeof(row.cityId)=="undefined")?row.cityId:"0"+row.cityId;
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
						iconCls:'icon-cancel',
						handler:function(){
							parent.$.modalDialog.handler.dialog('destroy');
							parent.$.modalDialog.handler = undefined;
						}
					}]
				});
			}else{
				parent.$.messager.show({
					title :"提示",
					msg :"请选择一行记录!",
					timeout : 1000 * 2
				});
			}
		}
		
		//删除
		function delRows(){
			var row = $dg.datagrid('getSelected');
			if(row){
				var rowIndex = $dg.datagrid('getRowIndex', row);
				parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){
					if(r){
						$dg.datagrid('deleteRow', rowIndex);
						$.ajax({
							url:"cst/cstAction!delCustomer.action",
							data: "customerId="+row.customerId,
							success: function(rsp){
								parent.$.messager.show({
									title : rsp.title,
									msg : rsp.message,
									timeout : 1000 * 2
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
	</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
					在此你可以对<span class="label-info"><strong>商品信息</strong></span>进行编辑!
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="cstAdd">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="cstEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="cstDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
						</shiro:hasPermission>
					</td>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:void(0);">高级查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm">
				<div name="name">商品名称</div>
				<div name="myid">商品编码</div>
		</div>
		<table id="dg" title="商品管理"></table>
  	</div>	
  </body>
</html>
