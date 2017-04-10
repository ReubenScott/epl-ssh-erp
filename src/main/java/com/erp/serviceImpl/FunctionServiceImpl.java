package com.erp.serviceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.erp.dao.PublicDao;
import com.erp.model.Permission;
import com.erp.service.FunctionService;
import com.erp.util.Constants;
import com.erp.viewModel.TreeGridModel;
import com.erp.viewModel.TreeModel;

@Service("functionService")
public class FunctionServiceImpl implements FunctionService
{
	private static final Logger logger=Logger.getLogger(FunctionServiceImpl.class);
	@Autowired
	private PublicDao<Permission> dao;
	
 	@Override
	public List<TreeGridModel> findAllFunctionList(Integer pid)
	{
 		String hql="from Permission t where t.status='A'";
 		if(pid==null||"".equals(pid))
 		{
 			hql+=" and t.pid is null";
 		}
 		else
 		{
 			hql+=" and t.pid="+pid;
 		}
 		List<Permission> list = dao.find(hql);
 		List<TreeGridModel> tempList=new ArrayList<TreeGridModel>();
 		for(Permission function:list)
 		{
 			TreeGridModel treeGridModel=new TreeGridModel();
 			try
			{
				BeanUtils.copyProperties(treeGridModel, function);
				if(pid==null||"".equals(pid))
				{
					treeGridModel.setPid(null);
				}
				tempList.add(treeGridModel);
				
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
 		}
 		return tempList;
	}

	@Override
	public List<TreeModel> findAllFunctionList()
	{
		String hql="from Permission t where t.status='A' and t.type='F'";
 		List<Permission> list=dao.find(hql);
 		List<TreeModel> templist=new ArrayList<TreeModel>();
 		for(Permission function:list)
 		{
 			TreeModel treeModel=new TreeModel();
 			treeModel.setId(function.getPermissionId().toString());
 			treeModel.setPid(function.getPid()==null?"":function.getPid().toString());
 			treeModel.setName(function.getName());
 			treeModel.setIconCls(function.getIconCls());
 			treeModel.setState(Constants.TREE_STATUS_OPEN);
 			templist.add(treeModel);
 		}
		return templist;
	}

	@Override
	public boolean persistenceFunction(List<Permission> list)
	{
		logger.debug("f");
		Integer userId=Constants.getCurrendUser().getUserId();
		for(Permission function:list)
		{
			function.setLastmod(new Date());
			function.setModifyer(userId);
			if (Constants.TREE_GRID_ADD_STATUS.equals(function.getStatus()))
			{
				function.setPermissionId(null);
				function.setCreated(new Date());
				function.setLastmod(new Date());
				function.setModifyer(userId);
				function.setCreater(userId);
				function.setStatus(Constants.PERSISTENCE_STATUS);
			}
			dao.saveOrUpdate(function);
		}
		return true;
	}

	@Override
	public boolean persistenceFunction(Permission permission)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		//permission为空就创建  
		if(null==permission.getPermissionId()||"".equals(permission.getPermissionId()))
		{
			permission.setCreated(new Date());
			permission.setLastmod(new Date());
			permission.setCreater(userId);
			permission.setModifyer(userId);
			permission.setStatus(Constants.PERSISTENCE_STATUS);
			if(Constants.IS_FUNCTION.equals(permission.getType()))
			{
				permission.setState(Constants.TREE_STATUS_CLOSED);
			}
			else
			{
				permission.setState(Constants.TREE_STATUS_OPEN);
			}
			dao.save(permission);
		}
		else
		{
			if(Constants.IS_FUNCTION.equals(permission.getType()))
			{
				permission.setState(Constants.TREE_STATUS_CLOSED);
			}
			else
			{
				permission.setState(Constants.TREE_STATUS_OPEN);
			}
			permission.setLastmod(new Date());
			permission.setModifyer(userId);
			dao.update(permission);
		}
		return true;
	}

	@Override
	public boolean delFunction(Integer id)
	{
		String hql=" from Permission t where t.status='A' and t.pid="+id;
		List<Permission> list=dao.find(hql);
		if(list!=null&&!list.isEmpty())
		{
			return false;
		}
		else
		{
			Permission function = dao.get(Permission.class, id);
			function.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			function.setLastmod(new Date());
			function.setModifyer(Constants.getCurrendUser().getUserId());
			dao.deleteToUpdate(function);
			return true;
		}
	}
	
}
