package com.commerce.library.service.impl;

import com.commerce.library.dao.ProductDao;
import com.commerce.library.model.Category;
import com.commerce.library.model.Product;
import com.commerce.library.repository.ProductRepository;
import com.commerce.library.service.ProductService;
import com.commerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDao> findAll() {
        List<ProductDao> productDaoList = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product: products){
            ProductDao productDao = new ProductDao();
            productDao.setId(product.getId());
            productDao.setName(product.getName());
            productDao.setDescription(product.getDescription());
            productDao.setCostPrice(product.getCostPrice());
            productDao.setSalePrice(product.getSalePrice());
            productDao.setCurrentQuantity(product.getCurrentQuantity());
            productDao.setCategory(product.getCategory());
            productDao.setImage(product.getImage());
            productDao.setActivated(product.is_activated());
            productDao.setDeleted(product.is_deleted());
            productDaoList.add(productDao);
        }
        return productDaoList;
    }



    //    exp for findById replace getById
//public void deleteById(Long id) {
//    Optional<Category> optionalCategory = repo.findById(id);
//    if (optionalCategory.isPresent()) {
//        Category category = optionalCategory.get();
//        category.set_deleted(true);
//        category.set_activated(false);
//        repo.save(category);
//    }
//}

    @Override
    public Product save(MultipartFile imageProduct, ProductDao productDao) {
        try {
            Product product =new Product();
            if(imageProduct == null){
                product.setImage(null);
            }else {
                if(imageUpload.uploadImage(imageProduct)){
                    System.out.println("update!!!!");
                }
//            img name
                product.setImage(imageProduct.getOriginalFilename());
            }
            product.setName(productDao.getName());
            product.setDescription(productDao.getDescription());
            product.setCategory(productDao.getCategory());
            product.setCostPrice(productDao.getCostPrice());
            product.setCurrentQuantity(productDao.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }
    }



    @Override
    public Product update(MultipartFile imageProduct,ProductDao productDao) {
        Optional<Product> optionalProduct = productRepository.findById(productDao.getId());
        if (optionalProduct.isPresent()){
            try{
                Product product = optionalProduct.get();
            if(imageProduct == null){
                product.setImage(product.getImage());
            }else {
                if (imageUpload.checkExisted(imageProduct) == false){

                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!update");

                    imageUpload.uploadImage(imageProduct);
                }

                System.out.println("have a img!!!!!!!!!!!!!!");
                product.setImage(imageProduct.getOriginalFilename());
            }
            product.setName(productDao.getName());
            product.setDescription(productDao.getDescription());
            product.setSalePrice(productDao.getSalePrice());;
            product.setCostPrice(productDao.getCostPrice());
            product.setCurrentQuantity(productDao.getCurrentQuantity());
            product.setCategory(productDao.getCategory());
            return productRepository.save(product);
        }catch (Exception e){
                e.printStackTrace();
                return null;
            }
    }
        return null;
    }

    //    Optional<Category> optionalCategory = repo.findById(id);
//    if (optionalCategory.isPresent()) {
//        Category category = optionalCategory.get();
//        category.set_deleted(true);
//        category.set_activated(false);
//        repo.save(category);
    @Override
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            product.get().set_deleted(true);
            product.get().set_activated(false);
            productRepository.save(product.get());
        }

    }

    @Override
    public void enableById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            product.get().set_deleted(false);
            product.get().set_activated(true);
            productRepository.save(product.get());
        }


    }

    @Override
    public ProductDao getByID(Long id) {
        Optional<Product> product = productRepository.findById(id);

        ProductDao productDao = new ProductDao();
        productDao.setId(product.get().getId());
        productDao.setName(product.get().getName());
        productDao.setDescription(product.get().getDescription());
        productDao.setCurrentQuantity(product.get().getCurrentQuantity());
        productDao.setCategory(product.get().getCategory());
        productDao.setSalePrice(product.get().getCostPrice());
        productDao.setCostPrice(product.get().getCostPrice());
        productDao.setImage(product.get().getImage());
        productDao.setDeleted(product.get().is_deleted());
        productDao.setActivated(product.get().is_activated());

        return productDao;
    }

    @Override
    public List<Product> pageProducts() {
        return null;
    }


    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    @Override
    public Page<Product> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo,5);
        Page<Product> productPages = productRepository.pageProduct(pageable);
        return productPages;
    }

    @Override
    public Page<Product> searchProducts(int pageNo,String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Product> products = productRepository.searchProducts(keyword, pageable);
        return products;

    }
}