package com.example.shardingsphere;

import com.example.shardingsphere.entity.Dict;
import com.example.shardingsphere.entity.Order;
import com.example.shardingsphere.mapper.DictMapper;
import com.example.shardingsphere.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ShardingsphereApplicationTests {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DictMapper dictMapper;


    /**
     * 分库分表
     */
    @Test
    void contextLoads() {

        for (int i = 1; i <= 20; i++) {

            Order order = new Order();
//            order.setOrderId(Long.valueOf(i));
            order.setCreateTime(new Date());
            orderMapper.insert(order);
        }
    }

    /**
     * 广播表插入
     * 会分别往配的数据源的广播表中插入数据
     */
    @Test
    void boardCastTable() {
        for (int i = 0; i < 10; i++) {
            Dict dict = new Dict();
            dict.setDictName("dict"+i);
            dictMapper.insert(dict);
        }

    }
    /**
     * 广播表 从当前选择的数据源中查询
     */
    @Test
    void queryBoardCastTable() {
        List<Dict> dicts = dictMapper.selectList(null);
        System.out.println(dicts);

    }

    /**
     * 读写分离测试  --> 读
     * slave库默为ds-1
     */
    @Test
    void queryReadList() {
        Dict dict = dictMapper.selectById(1L);
        System.out.println(dict);
    }

    /**
     * 读写分离测试  --> 写
     * slave库默为ds-1
     */
    @Test
    void queryWriteOrder() {
        Dict dict = new Dict();
        dict.setDictName("write-test");
        dictMapper.insert(dict);
    }

}
