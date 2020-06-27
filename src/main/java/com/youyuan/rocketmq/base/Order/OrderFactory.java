package com.youyuan.rocketmq.base.Order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.youyuan.rocketmq.base.util.GenerateCodeUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 类名称：OrderFactory <br>
 * 类描述： 订单工厂类 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 13:41<br>
 */
public class OrderFactory {

    public static List<OrderMsgBean> builder() {
        Long orderId1 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId2 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId3 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId4 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId5 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId6 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId7 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId8 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId9 = Long.valueOf(GenerateCodeUtil.generateCode());
        Long orderId10 = Long.valueOf(GenerateCodeUtil.generateCode());
        System.out.println("order1:"+orderId1);
        System.out.println("order2:"+orderId2);
        System.out.println("order3:"+orderId3);
        System.out.println("order4:"+orderId4);
        System.out.println("order5:"+orderId5);
        System.out.println("order6:"+orderId6);
        System.out.println("order7:"+orderId7);
        System.out.println("order8:"+orderId8);
        System.out.println("order9:"+orderId9);
        System.out.println("order10:"+orderId10);

        return Arrays.asList(new OrderMsgBean(orderId1, "创建1"),
                new OrderMsgBean(orderId1, "付款1"),
                new OrderMsgBean(orderId1, "推送1"),
                new OrderMsgBean(orderId1, "完成1"),
                new OrderMsgBean(orderId2, "创建2"),
                new OrderMsgBean(orderId2, "付款2"),
                new OrderMsgBean(orderId2, "推送2"),
                new OrderMsgBean(orderId3, "创建3"),
                new OrderMsgBean(orderId3, "付款3"),
                new OrderMsgBean(orderId3, "推送3"),
                new OrderMsgBean(orderId3, "完成3"),
                new OrderMsgBean(orderId4, "创建4"),
                new OrderMsgBean(orderId4, "付款4"),
                new OrderMsgBean(orderId4, "推送4"),
                new OrderMsgBean(orderId5, "创建5"),
                new OrderMsgBean(orderId5, "付款5"),
                new OrderMsgBean(orderId5, "推送5"),
                new OrderMsgBean(orderId6, "创建6"),
                new OrderMsgBean(orderId6, "付款6"),
                new OrderMsgBean(orderId7, "创建7"),
                new OrderMsgBean(orderId7, "付款7"),
                new OrderMsgBean(orderId8, "创建8"),
                new OrderMsgBean(orderId9, "创建9"),
                new OrderMsgBean(orderId10, "创建10"));
    }
}
