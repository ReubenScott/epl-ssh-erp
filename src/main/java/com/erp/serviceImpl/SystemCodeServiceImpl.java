package com.erp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.SystemCode;
import com.erp.service.SystemCodeService;
import com.erp.util.Constants;
import com.erp.viewModel.TreeModel;
@Service("systemCodeService")
public class SystemCodeServiceImpl implements SystemCodeService
{
	@Autowired
	private PublicDao<SystemCode> dao;
	
	@Override
	public List<SystemCode> findSystemCodeList(Integer id)
	{
		String hql="from SystemCode t where t.status='A' ";
		if(null==id||"".equals(id))
		{
			hql+=" and t.parentId is null";
		}
		else
		{
			hql+=" and t.parentId="+id;
		}
		return dao.find(hql);
	}

	@Override
	public List<TreeModel> findSystemCodeList()
	{
		String hql="from SystemCode t where t.status='A' ";
		List<SystemCode> list=dao.find(hql);
		List<TreeModel> tempList=new ArrayList<TreeModel>();
		for(SystemCode s:list)
		{
			TreeModel treeModel=new TreeModel();
			treeModel.setId(s.getCodeId().toString());
			treeModel.setPid(s.getParentId()==null?"":s.getParentId().toString());
			treeModel.setName(s.getName());
			treeModel.setIconCls(s.getIconCls());
			treeModel.setState("open");
			treeModel.setPermissionId(s.getPermissionId());
			tempList.add(treeModel);
		}
		return tempList;
	}

	@Override
	public boolean persistenceSystemCodeDig(SystemCode systemCode,
			String permissionName, Integer codePid)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		Integer pid=systemCode.getParentId();
		Integer codeId=systemCode.getCodeId();
		if(null==codeId||"".equals(codeId))
		{
			systemCode.setCreated(new Date());
			systemCode.setLastmod(new Date());
			systemCode.setCreater(userId);
			systemCode.setModifyer(userId);
			systemCode.setStatus(Constants.PERSISTENCE_STATUS);
			systemCode.setType("D");
			if(null==pid||"".equals(pid))
			{
				//没有父级就打开状态
				systemCode.setState(Constants.TREE_STATUS_OPEN);
			}
			else
			{
				//存在父级
				SystemCode code=dao.get(SystemCode.class, pid);
				if(!"closed".equals(code.getState()))
				{
					code.setState("closed");
					dao.update(code);
				}
				systemCode.setState(Constants.TREE_STATUS_OPEN);
			}
			String hql=" from SystemCode t where t.status='A' and t.type='M' and t.permissionId="+systemCode.getPermissionId();
			List<SystemCode> list=dao.find(hql);
			if(list!=null&&!list.isEmpty())
			{
				if (pid==null||"".equals(pid))
				{
					SystemCode sysc = list.get(0);
					systemCode.setParentId(sysc.getCodeId());
				}
			}
			else
			{
				SystemCode ss=new SystemCode();
				ss.setCreated(new Date());
				ss.setLastmod(new Date());
				ss.setCreater(userId);
				ss.setModifyer(userId);
				ss.setStatus(Constants.PERSISTENCE_STATUS);
				ss.setPermissionId(systemCode.getPermissionId());
				String[] temp=permissionName.split(",");
				ss.setName(temp[0]);
				ss.setState(Constants.TREE_STATUS_CLOSED);
				ss.setIconCls(temp[1]);
				ss.setType("M");
				dao.save(ss);
				systemCode.setParentId(ss.getCodeId());
			}
			dao.save(systemCode);
		}
		else
		{
			systemCode.setLastmod(new Date());
			systemCode.setModifyer(userId);
			dao.update(systemCode);
		}
		return true;
	}

	@Override
	public boolean delSystemCode(Integer codeId)
	{
		String hql="from SystemCode t where t.status='A' and t.parentId="+codeId;
		List<SystemCode> list=dao.find(hql);
		if(list!=null&&!list.isEmpty())
		{
			return false;
		}
		else
		{
			Integer userId=Constants.getCurrendUser().getUserId();
			SystemCode s=dao.get(SystemCode.class, codeId);
			s.setLastmod(new Date());
			s.setModifyer(userId);
			s.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			dao.deleteToUpdate(s);
			return true;
		}
	}

	@Override
	public List<SystemCode> findSystemCodeByType(String codeMyId)
	{
		String hql="from SystemCode t where t.status='A' and t.type='D' and t.codeMyid='"+codeMyId+"'";
		List<SystemCode> list=dao.find(hql);
		if(list.size()==1)
		{
			SystemCode ss = list.get(0);
			String hql2="from SystemCode t where t.status='A' and t.parentId="+ss.getCodeId();
			return dao.find(hql2);
		}
		return null;
	}

}
