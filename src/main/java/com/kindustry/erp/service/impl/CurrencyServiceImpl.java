package com.kindustry.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Currency;
import com.kindustry.erp.service.CurrencyService;
import com.kindustry.framework.dao.IBaseDao;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {
  @Autowired
  private IBaseDao<Currency> baseDao;

  @Override
  public List<Currency> findCurrencyList() {
    return baseDao.find("from Currency");
  }

}
