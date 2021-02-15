package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Brand;
import com.kindustry.erp.model.Item;
import com.kindustry.erp.service.ItemService;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service("itemService")
@SuppressWarnings("unchecked")
public class ItemServiceImpl extends BaseServiceImpl implements ItemService {

  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<Item> findItemList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Item t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Item t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, map);
  }

  @Override
  public List<Brand> findBrandList() {
    return baseDao.find("from Brand t where t.status='A'");
  }

  @Override
  public boolean persistenceItem(Item item) {
    String userId = super.getCurrendUser().getUserId();
    if (item.getItemId() == null || "".equals(item.getItemId())) {
      item.setCreated(new Date());
      item.setLastmod(new Date());
      item.setCreater(userId);
      item.setModifyer(userId);
      item.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(item);
    } else {
      item.setModifyer(userId);
      item.setLastmod(new Date());
      baseDao.update(item);
    }
    return true;
  }

  @Override
  public boolean addBrands(String name) {
    Brand b = new Brand();
    b.setCreated(new Date());
    b.setLastmod(new Date());
    b.setStatus("A");
    b.setName(name);
    b.setCreater(super.getCurrendUser().getUserId());
    b.setModifyer(super.getCurrendUser().getUserId());
    baseDao.save(b);
    return true;
  }

  @Override
  public boolean delItem(Integer itemId) {
    Item i = (Item)baseDao.get(Item.class, itemId);
    i.setLastmod(new Date());
    i.setModifyer(super.getCurrendUser().getUserId());
    i.setStatus("I");
    baseDao.update(i);
    return true;
  }

  @Override
  public Item findItemByMyid(String myid, Integer suplierId) {
    String hql = "from Item t where t.status='A' and t.myid='" + myid + "'";
    List<Item> list = baseDao.find(hql);
    if (list != null && list.size() != 0) {
      Item item = list.get(0);
      item.setImage(null);// 不需要图片数据
      if (suplierId != null && !"".equals(suplierId)) {
        String sql =
          "SELECT t.PRICE  from ORDER_PURCHASE_LINE t LEFT JOIN ORDER_PURCHASE op on t.ORDER_PURCHASE_ID=op.ORDER_PURCHASE_ID where op.SUPLIER_ID=" + suplierId
            + "  and   t.MYID='" + myid + "' and   t.LASTMOD=(\n"
            + " SELECT MAX(ot.LASTMOD) FROM ORDER_PURCHASE_LINE ot LEFT JOIN ORDER_PURCHASE tt on tt.ORDER_PURCHASE_ID=ot.ORDER_PURCHASE_ID\n" + " where tt.SUPLIER_ID="
            + suplierId + "  and  ot.STATUS='A' and ot.myid='" + myid + "' GROUP BY ot.myid\n" + ") \n" + "GROUP BY t.LASTMOD";
        @SuppressWarnings("rawtypes")
        List list2 = baseDao.findBySQL(sql);
        if (list2 != null && list2.size() != 0) {
          Double price = (list2.get(0) == null) ? 0 : Double.parseDouble(list2.get(0).toString());
          item.setCost(price);
        } else {
          item.setCost(0.0);
        }
      } else {
        item.setCost(0.0);
      }
      return item;
    } else {
      return null;
    }
  }

}
