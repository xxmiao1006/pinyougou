package com.pinyougou.search.service.impl;


import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * 监听类，用于将数据导入solr库
 */
@Component("itemSearchListener")
public class ItemSearchListener implements MessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            //获取JSON字符串
            String text = textMessage.getText();
            System.out.println("监听到消息" + text);
            //将JSON字符串转换成List
            List<TbItem> itemList = JSON.parseArray(text, TbItem.class);
            //将
            itemSearchService.importList(itemList);
            System.out.println("商品数据导入到solr索引库");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
