package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Bug;
import com.kindustry.erp.util.PageUtil;

public interface BugService {

  List<Bug> findBugList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean persistenceBug(Bug bug);

  boolean delBug(Integer bugId);

}
