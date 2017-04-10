package com.erp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.City;
import com.erp.model.Province;
import com.erp.service.AreaService;
import com.erp.util.Constants;
import com.erp.viewModel.Attributes;
import com.erp.viewModel.TreeModel;
@Service("areaService")
@SuppressWarnings("unchecked")
public class AreaServiceImpl implements AreaService
{
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private PublicDao dao;
	@Override
	public List<TreeModel> findCities()
	{
		List<TreeModel> list=new ArrayList<TreeModel>();
		String hql="from Province t where t.status='A' ";
		String hql2=" from City t where t.status='A' ";
		List<Province> list1=dao.find(hql);
		for(Province province:list1)
		{
			TreeModel t=new TreeModel();
			t.setId(province.getProvinceId()+"");
			t.setName(province.getName());
			t.setPid(null);
			t.setState("closed");
			Attributes attributes=new Attributes();
			attributes.setStatus("p");
			t.setAttributes(attributes);
			list.add(t);
		}
		List<City> list2=dao.find(hql2);
		for(City city:list2)
		{
			TreeModel t=new TreeModel();
			t.setId("0"+city.getCityId());
			t.setName(city.getName());
			t.setPid(city.getProvinceId()+"");
			t.setState("open");
			Attributes attributes=new Attributes();
			attributes.setStatus("c");
			t.setAttributes(attributes);
			list.add(t);
		}
		return list;
	}

	@Override
	public List<Province> findProvinces()
	{
		return dao.find("from Province t where t.status='A'");
	}

	@Override
	public boolean addCities(City city)
	{
		city.setCreated(new Date());
		city.setLastmod(new Date());
		city.setStatus("A");
		city.setCreater(Constants.getCurrendUser().getUserId());
		city.setModifyer(Constants.getCurrendUser().getUserId());
		dao.save(city);
		return true;
	}

}
