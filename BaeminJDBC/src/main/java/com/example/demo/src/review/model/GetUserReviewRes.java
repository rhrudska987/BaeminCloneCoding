package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserReviewRes {
    private String profileImage;
    private String grade;
    private String userName;
    private int totalReview;
    private double avgReview;
    private int fivePoint;
    private int fourPoint;
    private int threePoint;
    private int twoPoint;
    private int onePoint;
    private Review[] reviewArr;
}
