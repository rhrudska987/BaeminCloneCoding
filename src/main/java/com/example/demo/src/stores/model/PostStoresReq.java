package com.example.demo.src.stores.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostStoresReq {
    private String storeName;
    private String telephoneNumber;
    private String address;
    private String comment;
    private String minimumOrderAmount;
    private String storeImage;
    private String storeImage1;
    private String storeImage2;
    private String storeImage3;
    private String storeImage4;
    private String deliveryTip;
}
