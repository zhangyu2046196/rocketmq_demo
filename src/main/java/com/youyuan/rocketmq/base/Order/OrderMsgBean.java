package com.youyuan.rocketmq.base.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 类名称：OrderMsgBean <br>
 * 类描述： 订单基础类 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 13:37<br>
 */
@Data
@AllArgsConstructor
public class OrderMsgBean implements Serializable {
    private static final long serialVersionUID = -2569362213689689237L;
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 订单描述
     */
    private String desc;
}
