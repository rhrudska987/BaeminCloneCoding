package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReviewReq {
    private int userId;
    private int storeId;
    private double starPoint;
    private String menuName;
    private String comment;
    private String reviewImage1;
    private String reviewImage2;
    private String reviewImage3;
    private String reviewImage4;
}
