package com.example.demo.src.stores.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenu {
    private String menuName;
    private String menuImage;
    private int menuPrice;
    private String comment;
}
