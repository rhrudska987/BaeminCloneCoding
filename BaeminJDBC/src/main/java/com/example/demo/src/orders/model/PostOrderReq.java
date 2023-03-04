package com.example.demo.src.orders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderReq {
    private int userId;
    private String storeName;
    private int orderPrice;
    private int deliveryTip;
    private int totalPrice;
    private String receipt;
    private String commentStore;
    private String commentRider;
    private String orderNumber;
}
