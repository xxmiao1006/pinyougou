package com.pinyougou.manager.controller;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    //因为BrandService的实现类是用dubbo的@Service修饰，所以不能用@AutoWired来获取
    @com.alibaba.dubbo.config.annotation.Reference
    private BrandService brandService;

    //查询所有数据
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    //查询分页数据
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int size) {
        return brandService.findPage(page, size);
    }

    //增加品牌
    //@RequestBody指定参数读取请求体中的数据，请求方式必须为POST
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "增加品牌成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加品牌失败!");
        }
    }

    //根据id查询品牌
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id) {
        return brandService.findById(id);
    }

    //修改品牌
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand) {
        try {
            brandService.update(brand);
            return new Result(true, "修改品牌成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改品牌失败!");
        }
    }

    //删除品牌
    @RequestMapping("/delete")
    public Result delete(Long ids[]) {
        try {
            brandService.delete(ids);
            return new Result(true, "删除品牌成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除品牌失败!");
        }
    }

    //条件查询
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, int page, int size) {
        //System.out.println(brand);
        return brandService.findPage(brand, page, size);
    }

    //显示品牌下拉列表
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        //返回特定json数据，通过修改配置Mybatis配置文件中的resultType属性指定Map集合
        return brandService.selectOptionList();
    }

}
