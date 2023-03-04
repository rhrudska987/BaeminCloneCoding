package com.example.demo.src.stores.model;

import com.example.demo.src.orders.model.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoresRes {
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
    private StoreMenu[] storeMenus;

    public GetStoresRes(String storeImage1, String storeName, double totalStarPoint, int minimumOrderAmount, String deliveryTip, String status) {
        this.storeImage1 = storeImage1;
        this.storeName = storeName;
        this.totalStarPoint = totalStarPoint;
        this.minimumOrderAmount = minimumOrderAmount;
        this.deliveryTip = deliveryTip;
        this.status = status;
    }

    public GetStoresRes(String storeImage1, String storeImage2, String storeImage3, String storeImage4, String storeName, int minimumOrderAmount, String deliveryTip, String comment, String telephoneNumber, String address, String status) {
        this.storeImage1 = storeImage1;
        this.storeImage2 = storeImage2;
        this.storeImage3 = storeImage3;
        this.storeImage4 = storeImage4;
        this.storeName = storeName;
        this.minimumOrderAmount = minimumOrderAmount;
        this.deliveryTip = deliveryTip;
        this.comment = comment;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.status = status;
    }
}
