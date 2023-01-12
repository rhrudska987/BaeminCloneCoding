package com.example.demo.src.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCouponRes {
    private String couponPrice;
    private String couponName;
    private String minimumOrderAmount;
    private String createAt;
    private String expiredAt;
    private String couponImage;
}
