package com.commerce.library.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDao {
    private Long categoryId;
    private String categoryName;
    private Long numberOfProduct;
}