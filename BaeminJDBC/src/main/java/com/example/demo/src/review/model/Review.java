package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String storeName;
    private double starPoint;
    private String createAt;
    private String reviewImage1;
    private String reviewImage2;
    private String reviewImage3;
    private String reviewImage4;
    private String comment;
    private String menuName;
    private String hostComment;
}
