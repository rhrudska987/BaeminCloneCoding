package com.example.demo.src.heart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchHeartReq {
    private int heartId;
    private int userId;
    private String status;
}
