package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

//搜索功能接口
public interface ItemSearchService {

    /**
     * 关键字搜索
     * @param searchMap
     * @return
     */
    public Map<String,Object> search(Map searchMap);

    /**
     * 导入数据
     * @param list
     */
    public void importList(List list);

    /**
     * 删除数据
     * @param goodsIds(SPU)
     */
    public void deleteByGoodsIds(List goodsIds);



}
