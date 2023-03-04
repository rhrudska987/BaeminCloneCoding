package com.example.demo.src.stores.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchStoreReq {
    private String storeImage1;
    private String storeImage2;
    private String storeImage3;
    private String storeImage4;
    private String storeName;
    private int minimumOrderAmount;
    private String deliveryTip;
    private String comment;
    private String telephoneNumber;
    private String address;
    private String status;
}
