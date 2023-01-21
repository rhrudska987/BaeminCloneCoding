package com.example.demo.src.category.model;

import com.example.demo.src.stores.model.StoreMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoryRes {
    private String categoryName;
    private String categoryImage;
}
