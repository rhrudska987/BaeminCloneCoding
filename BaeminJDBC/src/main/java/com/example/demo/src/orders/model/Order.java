package com.example.demo.src.orders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;
    private String orderStatus;
    private int userId;
    private String storeName;
    private Timestamp createAt;
    private Timestamp updateAt;
    private int orderPrice;
    private int deliveryTip;
    private int totalPrice;
    private String cancelStatus;
    private String reviewStatus;
    private String status;
    private String receipt;
    private String commentStore;
    private String commentRider;
    private String orderNumber;
}
