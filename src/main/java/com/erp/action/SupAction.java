package com.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.erp.model.Suplier;
import com.erp.model.SuplierContact;
import com.erp.service.SupService;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
import com.erp.viewModel.GridModel;
import com.opensymphony.xwork2.ModelDriven;
@Namespace("/sup")
@Action("supAction")
public class SupAction extends BaseAction implements ModelDriven<Suplier>
{
	private static final long serialVersionUID = 5557229007665414931L;
	@Autowired
	private SupService supService;
	private Suplier suplier;
	
	public Suplier getSuplier()
	{
		return suplier;
	}

	public void setSuplier(Suplier suplier)
	{
		this.suplier = suplier;
	}

	@Override
	public Suplier getModel()
	{
		if(suplier==null)
		{
			suplier=new Suplier();
		}
		return suplier;
	}

	/**
	 * 查询所有客户列表
	 */
	public void findSuplierList()
	{
		Map<String, Object> map=new HashMap<String, Object>();
		if (null!=searchValue&&!"".equals(searchValue))
		{
			map.put(searchName, Constants.GET_SQL_LIKE+searchValue+Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil=new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel=new GridModel();
		gridModel.setRows(supService.findSuplierList(map, pageUtil));
		gridModel.setTotal(supService.getCount(map,pageUtil));
		OutputJson(gridModel);
	}
	
	/**
	 * 无分页查询所有供应商
	 */
	public void findSuplierListNoPage()
	{
		Map<String, Object> map=new HashMap<String, Object>();
		if (null!=searchValue&&!"".equals(searchValue))
		{
			map.put(searchName, Constants.GET_SQL_LIKE+searchValue+Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil=new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel=new GridModel();
		gridModel.setRows(supService.findSuplierListNoPage(map, pageUtil));
		gridModel.setTotal(null);
		OutputJson(gridModel);
	}
	
	/**
	 * 删除Suplier
	 */
	public void delSuplier()
	{
		OutputJson(getMessage(supService.delSuplier(getModel().getSuplierId())));
	}
	
	/**
	 * 持久化Suplier
	 */
	public void persistenceSuplier()
	{
		Map<String, List<SuplierContact>> map=new HashMap<String, List<SuplierContact>>();
		if (inserted!=null&&!"".equals(inserted))
		{
			map.put("addList", JSON.parseArray(inserted, SuplierContact.class));
		}
		if (updated!=null&&!"".equals(updated))
		{
			map.put("updList", JSON.parseArray(updated, SuplierContact.class));
		}
		if (deleted!=null&&!"".equals(deleted))
		{
			map.put("delList", JSON.parseArray(deleted, SuplierContact.class));
		}
		OutputJson(getMessage(supService.persistenceSuplier(getModel(),map)),Constants.TEXT_TYPE_PLAIN);
	}
	
	/**
	 * 查询供应商联系人
	 */
	public void findSuplierContactList()
	{
		GridModel gridModel=new GridModel();
		gridModel.setRows(supService.findSuplierContactList(getModel().getSuplierId()));
		gridModel.setTotal(null);
		OutputJson(gridModel);
	}
	
	/**
	 * 查询供应商联系人下拉框格式
	 */
	public void findSuplierContactListCombobox()
	{
		OutputJson(supService.findSuplierContactList(getModel().getSuplierId()));
	}
}
