package com.commerce.library.service;

import com.commerce.library.dao.CategoryDao;
import com.commerce.library.model.Category;
import com.commerce.library.model.Product;

import java.util.List;

public interface CategoryService {
    /*Admin*/
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enabledById(Long id);
    List<Category> findAllByActivated();

    /*Customer*/
    List<CategoryDao> getCategoryAndProduct();


}
