package com.erp.serviceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Parameter;
import com.erp.service.SystemParameterService;
import com.erp.shiro.ShiroUser;
import com.erp.util.Constants;
import com.erp.viewModel.CheckBoxModel;
import com.erp.viewModel.Options;
import com.erp.viewModel.ParameterModel;
@Service("systemParameterService")
public class SystemParameterServiceImpl implements SystemParameterService
{
	@Autowired
	private PublicDao<Parameter> dao;
	
	@Override
	public boolean persistenceParameter(Map<String, List<Parameter>> map)
	{
		this.addParameter(map.get("addList"));
		this.updParameter(map.get("updList"));
		this.delParameter(map.get("delList"));
		return true;
	}

	private boolean delParameter(List<Parameter> delList)
	{
		if(delList!=null&&!delList.isEmpty())
		{
			for(Parameter p:delList)
			{
				p.setLastmod(new Date());
				p.setModifyer(Constants.getCurrendUser().getUserId());
				p.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
				dao.deleteToUpdate(p);
			}
		}
		return true;
	}

	private boolean addParameter(List<Parameter> addlist)
	{
		if(addlist!=null&&!addlist.isEmpty())
		{
			ShiroUser users=Constants.getCurrendUser();
			for(Parameter p:addlist)
			{
				p.setCreated(new Date());
				p.setLastmod(new Date());
				p.setStatus("A");
				p.setCreater(users.getUserId());
				p.setModifyer(users.getUserId());
				dao.save(p);
			}
		}
		return true;
	}
	
	private boolean updParameter(List<Parameter>  updlist)
	{
		if(updlist!=null&&!updlist.isEmpty())
		{
			ShiroUser user=Constants.getCurrendUser();
			for(Parameter p:updlist)
			{
				p.setLastmod(new Date());
				p.setModifyer(user.getUserId());
				dao.update(p);
			}
		}
		return true;
	}

	@Override
	public List<ParameterModel> findParameterList(String type)
	{
		String hql = "from Parameter t where t.status='A'";
		List<Parameter> temp = dao.find(hql);
		List<ParameterModel> list2 = new ArrayList<ParameterModel>();
		for (Parameter p : temp)
		{
			ParameterModel pm = new ParameterModel();
			try
			{
				BeanUtils.copyProperties(pm, p);
				if ("checkbox".equals(p.getEditorType()))
				{
					CheckBoxModel cm = new CheckBoxModel();
					cm.setType("checkbox");
					cm.setOptions(new Options());
					pm.setEditor(cm);
				} else
				{
					pm.setEditor(p.getEditorType());
				}
				list2.add(pm);
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
		return list2;
	}
	
	
	
	
	
}