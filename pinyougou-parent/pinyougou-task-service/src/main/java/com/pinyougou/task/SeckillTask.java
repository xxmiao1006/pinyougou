package com.pinyougou.task;

import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillGoodsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SeckillTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;


    /**
     * 刷新秒杀商品
     */
    @Scheduled(cron="0/10 * * * * ?")
    public void refreshSeckillGoods(){
        System.out.println("执行了秒杀商品增量更新 任务调度"+new Date());
        //查询所有的秒杀商品ID集合
        List ids = new ArrayList( redisTemplate.boundHashOps("seckillGoods").keys());
        System.out.println("缓存中已有的秒杀商品ID" + ids);
        //查询正在秒杀的商品列表
        TbSeckillGoodsExample example=new TbSeckillGoodsExample();
        TbSeckillGoodsExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");//审核通过
        criteria.andStockCountGreaterThan(0);//剩余库存大于0
        criteria.andStartTimeLessThanOrEqualTo(new Date());//开始时间小于等于当前时间
        criteria.andEndTimeGreaterThan(new Date());//结束时间大于当前时间

        if(ids.size()>0) {
            criteria.andIdNotIn(ids);//排除缓存中已经有的商品
        }


        List<TbSeckillGoods> seckillGoodsList= seckillGoodsMapper.selectByExample(example);
        //装入缓存
        for( TbSeckillGoods seckill:seckillGoodsList ){
            redisTemplate.boundHashOps("seckillGoods").put(seckill.getId(), seckill);
            System.out.println("将商品ID为 "+seckill.getId()+" 进行增量更新");
        }

    }

    /**
     * 移除缓存中过期的秒杀商品
     */
    @Scheduled(cron="0/2 * * * * ?")
    public void removeSeckillGoods(){

        //扫描缓存中秒杀商品列表，发现过期的移除
        List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGoods").values();
        System.out.println("移除秒杀商品任务在执行" + new Date());
        for( TbSeckillGoods seckill:seckillGoodsList ){
            if(seckill.getEndTime().getTime()<new Date().getTime()  ){//如果结束日期小于当前日期，则表示过期
                seckillGoodsMapper.updateByPrimaryKey(seckill);//向数据库保存记录
                redisTemplate.boundHashOps("seckillGoods").delete(seckill.getId());//移除缓存数据
                System.out.println("移除秒杀商品"+seckill.getId());
            }
        }
        System.out.println("移除秒杀商品任务结束");
    }


}

