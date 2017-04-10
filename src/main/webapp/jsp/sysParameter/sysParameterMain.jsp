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
    <title>系统参数</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
		var $pg;
		$(function(){
			$pg=$("#pg");
			$pg.propertygrid({
				width:'auto',
				height:$(this).height()-90,
				showGroup:true,
				toolbar:"#tb",
				url:"systemParameter/systemParameterAction!findParameterList.action",
				columns:[[
					{field:'name',title:'参数名称',width:parseInt($(this).width()*0.1),sortable:true},
					{field:'myid',title:'参数编码',width:parseInt($(this).width()*0.1),sortable:true},
					{field:'state',title:'是否启用',width:parseInt($(this).width()*0.05),sortable:true,
						formatter:function(value,row){
							if("Y"==row.state)
								return "<font color=green>是<font>";
							return "<font color=red>否<font>";
						},
						editor:{type:'checkbox',options:{on:'Y',off:'N'}}
					},
					 {field:'value',title:'参数值',width:parseInt($(this).width()*0.05),
											formatter:function(value,row){
				            		  	  if(row.value=="true"){
												return "<font color=green>是<font>";
				            		  	  }else if(row.value=="false"){
				            		  		return "<font color=red>否<font>"; 
				            		  	  }else{
				            		  		  return row.value;
				            		  	  }
										}},
					{field:'description',title:'参数描述',width:parseInt($(this).width()*0.2),editor:"text"}
				]]
			});
		});
		
		function endEdit(){
			var flag=true;
			var rows=$pg.propertygrid('getRows');
			for(var i=0;i<rows.length;i++){
				$pg.propertygrid('endEdit',i);
				var temp=$pg.propertygrid('validateRow', i);
				if(!temp){flag=false;}
			}
			return flag;
		}
		
		function saveRows(){
			if(endEdit()){
				if($pg.propertygrid('getChanges').length){
					var inserted=$pg.propertygrid('getChanges', "inserted");
					var deleted = $pg.propertygrid('getChanges', "deleted");
					var updated = $pg.propertygrid('getChanges', "updated");
					var effectRow = new Object();
					if(inserted.length){
						effectRow["inserted"]=JSON.stringify(inserted);
					}
					if(deleted.length){
						effectRow["deleted"]=JSON.stringify(deleted);
					}
					if (updated.length) {
							effectRow["updated"] = JSON.stringify(updated);
					}
					$.post("systemParameter/systemParameterAction!persistenceCompanyInfo.action",effectRow,function(rsp){
						if(rsp.status){
							$pg.propertygrid('acceptChanges');
						}
						parent.$.messager.show({
							title:rsp.title,
							msg:rsp.message,
							timeout:1000*2
						});
					},"json").error(function(){
						parent.$.messager.show({
							title:'提示',
							message:'提交错误',
							timeout:1000*2
						});					
					});
				}else{
					parent.$.messager.show({
						title :"提示",
						msg :"字段验证未通过!请查看",
						timeout : 1000 * 2
					});
				}
			}
		}
		
		function removeRows(){
			var rows=$pg.propertygrid('getSelections');
			$.each(rows,function(i,row){
				if(row){
					var rowIndex = $pg.propertygrid('getRowIndex', row);
					$pg.propertygrid('deleteRow', rowIndex);
				}
			});
		}
	</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>系统参数</strong></span>进行是设置和编辑!
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="parEndEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-end" plain="true" onclick="endEdit();">结束编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="parDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRows();">删除</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="parSave">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveRows();">保存修改</a>
						</shiro:hasPermission>
					</td>
					<!--  <td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tbsCompanySearch();">高级查询</a>
					</td>-->
				</tr>
			</table>
		</div>
		<!--  <div id="mm">
				<div name="name">公司名称</div>
				<div name="tel">公司电话</div>
				<div name="fax">传真</div>
				<div name="address">地址</div>
				<div name="zip">邮政编码</div>
				<div name="email">邮箱</div>
				<div name="contact">联系人</div>
				<div name="description">描述</div>
		</div>-->
		<table id="pg" title="参数编辑"></table>
  	</div>	
  </body>
</html>
