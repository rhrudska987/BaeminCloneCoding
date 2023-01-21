package com.example.demo.src.heart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHeartRes {
    private String storeImage;
    private String storeName;
    private double totalStarPoint;
    private int minimumOrderAmount;
    private String deliveryTip;
    private String status;

    public GetHeartRes(String status){
        this.status = status;
    }

}
