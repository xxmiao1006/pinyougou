package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌接口
 */
public interface BrandService {

    public List<TbBrand> findAll();

    //查询分页数据
    public PageResult findPage(int pageNum,int pageSize);

    //增加品牌
    public void add(TbBrand brand);

    //根据id查询
    public TbBrand findById(Long id);

    //修改品牌
    public void update(TbBrand brand);

    //删除品牌
    public void delete(Long ids[]);

    //根据条件查询分页数据
    public PageResult findPage(TbBrand brand,int pageNum,int pageSize);


     //品牌下拉框数据
    List<Map> selectOptionList();

}
