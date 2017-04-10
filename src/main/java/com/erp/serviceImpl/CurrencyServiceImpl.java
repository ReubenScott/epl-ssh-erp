package com.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Currency;
import com.erp.service.CurrencyService;
@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService
{
	@Autowired
	private PublicDao<Currency> dao;
	
	@Override
	public List<Currency> findCurrencyList()
	{
		return dao.find("from Currency");
	}

}
