package com.kindustry.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.Currency;
import com.kindustry.erp.service.CurrencyService;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {
  @Autowired
  private PublicDao<Currency> dao;

  @Override
  public List<Currency> findCurrencyList() {
    return dao.find("from Currency");
  }

}
