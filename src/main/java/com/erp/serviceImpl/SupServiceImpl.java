package com.erp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Suplier;
import com.erp.model.SuplierContact;
import com.erp.service.SupService;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
@Service("supService")
@SuppressWarnings("unchecked")
public class SupServiceImpl implements SupService
{
	@SuppressWarnings("rawtypes")
	@Autowired
	private PublicDao dao;
	
	
	@Override
	public List<Suplier> findSuplierList(Map<String, Object> map,
			PageUtil pageUtil)
	{
		String hql="from Suplier t where t.status='A'";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return dao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
	}

	@Override
	public Long getCount(Map<String, Object> map, PageUtil pageUtil)
	{
		String hql="select count(*) from Suplier t where t.status='A' ";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return dao.count(hql, map);
	}

	@Override
	public boolean delSuplier(Integer suplierId)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		Suplier c=(Suplier) dao.get(Suplier.class, suplierId);
		c.setLastmod(new Date());
		c.setModifiyer(userId);
		c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		dao.deleteToUpdate(c);
		String hql="from SuplierContact t where t.status='A' and t.suplierId="+suplierId;
		List<SuplierContact> list=dao.find(hql);
		for(SuplierContact cus : list)
		{
			cus.setLastmod(new Date());
			cus.setModifyer(userId);
			cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			dao.deleteToUpdate(cus);
		}
		return true;
	}

	@Override
	public boolean persistenceSuplier(Suplier c,
			Map<String, List<SuplierContact>> map)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		if (c.getSuplierId()==null||"".equals(c.getSuplierId()))
		{
			c.setCreated(new Date());
			c.setLastmod(new Date());
			c.setCreater(userId);
			c.setModifiyer(userId);
			c.setStatus(Constants.PERSISTENCE_STATUS);
			dao.save(c);
			List<SuplierContact> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (SuplierContact cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					dao.save(cus);
				}
			}
		}else {
			c.setLastmod(new Date());
			c.setModifiyer(userId);
			dao.update(c);
			List<SuplierContact> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (SuplierContact cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					dao.save(cus);
				}
			}
			List<SuplierContact> updList = map.get("updList");
			if (updList!=null&&updList.size()!=0)
			{
				for (SuplierContact cus : updList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					dao.update(cus);
				}
			}
			List<SuplierContact> delList = map.get("delList");
			if (delList!=null&&delList.size()!=0)
			{
				for (SuplierContact cus : delList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
					dao.deleteToUpdate(cus);
				}
			}
		}
		return true;
	}

	@Override
	public List<SuplierContact> findSuplierContactList(Integer suplierId)
	{
		if (null==suplierId||"".equals(suplierId))
		{
			return new ArrayList<SuplierContact>();
		}else {
			String hql="from SuplierContact t where t.status='A' and t.suplierId="+suplierId;
			return dao.find(hql);
		}
	}

	@Override
	public List<Suplier> findSuplierListNoPage(Map<String, Object> map, PageUtil pageUtil)
	{
		String hql="from Suplier t where t.status='A'";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return dao.find(hql, map);
	}

}
