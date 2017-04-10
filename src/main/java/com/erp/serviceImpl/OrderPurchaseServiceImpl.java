package com.erp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.OrderPurchase;
import com.erp.model.OrderPurchaseLine;
import com.erp.service.OrderPurchaseService;
import com.erp.util.Constants;
import com.erp.util.PageUtil;

@Service("orderPurchaseService")
@SuppressWarnings("unchecked")
public class OrderPurchaseServiceImpl implements OrderPurchaseService
{
	@SuppressWarnings("rawtypes")
	@Autowired
	private PublicDao dao;

	@Override
	public List<OrderPurchase> findPurchaseOrderList(Map<String, Object> map, PageUtil pageUtil)
	{
		String hql = "from OrderPurchase t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", map);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return dao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
	}

	@Override
	public Long getCount(Map<String, Object> map, PageUtil pageUtil)
	{
		String hql = "select count(*) from OrderPurchase t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", map);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return dao.count(hql, map);
	}

	@Override
	public boolean delOrderPurchase(Integer orderPurchaseId)
	{
		Integer userId = Constants.getCurrendUser().getUserId();
		OrderPurchase c = (OrderPurchase) dao.get(OrderPurchase.class, orderPurchaseId);
		c.setLastmod(new Date());
		c.setModifyer(userId);
		c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		dao.deleteToUpdate(c);
		String hql = "from OrderPurchaseLine t where t.status='A' and t.orderPurchaseId=" + orderPurchaseId;
		List<OrderPurchaseLine> list = dao.find(hql);
		for (OrderPurchaseLine cus : list)
		{
			cus.setLastmod(new Date());
			cus.setModifyer(userId);
			cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			dao.deleteToUpdate(cus);
		}
		return true;
	}

	@Override
	public List<OrderPurchaseLine> findPurchaseOrderLineList(Integer orderPurchaseId)
	{
		if (orderPurchaseId==null||"".equals(orderPurchaseId))
		{
			return new ArrayList<OrderPurchaseLine>();
		}else {
			String hql="from OrderPurchaseLine t where t.status='A' and t.orderPurchaseId="+orderPurchaseId;
			return  dao.find(hql);
		}
	}

	@Override
	public boolean persistenceOrderPurchase(OrderPurchase c, Map<String, List<OrderPurchaseLine>> map)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		if (c.getOrderPurchaseId()==null||"".equals(c.getOrderPurchaseId()))
		{
			c.setCreated(new Date());
			c.setLastmod(new Date());
			c.setCreater(userId);
			c.setModifyer(userId);
			c.setStatus(Constants.PERSISTENCE_STATUS);
			dao.save(c);
			List<OrderPurchaseLine> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (OrderPurchaseLine cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					dao.save(cus);
				}
			}
		}else {
			c.setLastmod(new Date());
			c.setModifyer(userId);
			dao.update(c);
			List<OrderPurchaseLine> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (OrderPurchaseLine cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					dao.save(cus);
				}
			}
			List<OrderPurchaseLine> updList = map.get("updList");
			if (updList!=null&&updList.size()!=0)
			{
				for (OrderPurchaseLine cus : updList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					dao.update(cus);
				}
			}
			List<OrderPurchaseLine> delList = map.get("delList");
			if (delList!=null&&delList.size()!=0)
			{
				for (OrderPurchaseLine cus : delList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
					dao.deleteToUpdate(cus);
				}
			}
		}
		return true;
	}
	
}
