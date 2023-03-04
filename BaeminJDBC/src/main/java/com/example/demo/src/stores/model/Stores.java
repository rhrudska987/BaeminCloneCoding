package com.example.demo.src.stores.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stores {
    private String storeImage1;
    private String storeImage2;
    private String storeImage3;
    private String storeImage4;
    private String storeName;
    private double totalStarPoint;
    private int totalReview;
    private int totalHostComment;
    private int totalHeart;
    private int minimumOrderAmount;
    private String deliveryTip;
    private String comment;
    private String telephoneNumber;
    private String address;
    private String status;

    public Stores(String storeImage1, String storeName, double totalStarPoint, int minimumOrderAmount, String deliveryTip) {
        this.storeImage1 = storeImage1;
        this.storeName = storeName;
        this.totalStarPoint = totalStarPoint;
        this.minimumOrderAmount = minimumOrderAmount;
        this.deliveryTip = deliveryTip;
    }
}

