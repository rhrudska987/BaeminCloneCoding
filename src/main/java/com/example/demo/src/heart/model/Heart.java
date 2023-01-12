package com.example.demo.src.heart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Heart {
    private int heartId;
    private int userId;
    private int storeId;
    private String status;
    private String createAt;
    private String updateAt;
}
