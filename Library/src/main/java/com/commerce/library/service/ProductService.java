package com.commerce.library.service;

import com.commerce.library.dao.ProductDao;
import com.commerce.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<ProductDao> findAll();
    Product save(MultipartFile multipartFile, ProductDao productDao);
    Product update(MultipartFile multipartFile, ProductDao productDao);
    void deleteById(Long id);
    void enableById(Long id);

    ProductDao getByID(Long id);

    List<Product> pageProducts();

    List<Product> searchProducts(String keyword);

    Page<Product> pageProducts(int pageNo);

    Page<Product> searchProducts(int pageNo, String keyword);
}
