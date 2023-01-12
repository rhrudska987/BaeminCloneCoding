package com.example.demo.src.orders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderRes {
    private String orderStatus;
    private String storeName;
    private String createAt;
    private String orderNumber;
    private int orderPrice;
    private int deliveryTip;
    private int totalPrice;
    private String destinationAddress;
    private String commentStore;
    private String commentRider;
    private OrderMenu[] orderMenus;
}
