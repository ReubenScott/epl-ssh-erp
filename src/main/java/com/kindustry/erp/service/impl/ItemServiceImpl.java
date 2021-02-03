package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.Brand;
import com.kindustry.erp.model.Item;
import com.kindustry.erp.service.ItemService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;

@Service("itemService")
@SuppressWarnings("unchecked")
public class ItemServiceImpl implements ItemService {
  @SuppressWarnings("rawtypes")
  @Autowired
  private PublicDao dao;

  @Override
  public List<Item> findItemList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Item t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Item t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.count(hql, map);
  }

  @Override
  public List<Brand> findBrandList() {
    return dao.find("from Brand t where t.status='A'");
  }

  @Override
  public boolean persistenceItem(Item item) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (item.getItemId() == null || "".equals(item.getItemId())) {
      item.setCreated(new Date());
      item.setLastmod(new Date());
      item.setCreater(userId);
      item.setModifyer(userId);
      item.setStatus(Constants.PERSISTENCE_STATUS);
      dao.save(item);
    } else {
      item.setModifyer(userId);
      item.setLastmod(new Date());
      dao.update(item);
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
    b.setCreater(Constants.getCurrendUser().getUserId());
    b.setModifyer(Constants.getCurrendUser().getUserId());
    dao.save(b);
    return true;
  }

  @Override
  public boolean delItem(Integer itemId) {
    Item i = (Item)dao.get(Item.class, itemId);
    i.setLastmod(new Date());
    i.setModifyer(Constants.getCurrendUser().getUserId());
    i.setStatus("I");
    dao.deleteToUpdate(i);
    return true;
  }

  @Override
  public Item findItemByMyid(String myid, Integer suplierId) {
    String hql = "from Item t where t.status='A' and t.myid='" + myid + "'";
    List<Item> list = dao.find(hql);
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
        List list2 = dao.findBySQL(sql);
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
