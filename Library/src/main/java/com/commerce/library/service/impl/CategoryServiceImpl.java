package com.commerce.library.service.impl;

import com.commerce.library.dao.CategoryDao;
import com.commerce.library.model.Category;
import com.commerce.library.repository.CategoryRepository;
import com.commerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getName());
        return repo.save(categorySave);
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate= repo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        return repo.save(categoryUpdate);
    }

//    findById(id) 替代 getById(id)
@Override
public void deleteById(Long id) {
    Optional<Category> optionalCategory = repo.findById(id);
    if (optionalCategory.isPresent()) {
        Category category = optionalCategory.get();
        category.set_deleted(true);
        category.set_activated(false);
        repo.save(category);
    }
}

    @Override
    public void enabledById(Long id) {
        Optional<Category> optionalCategory = repo.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.set_activated(true);
            category.set_deleted(false);
            repo.save(category);
        }
    }


    @Override
    public List<Category> findAllByActivated() {
        return repo.findAllByActivated();
    }

    @Override
    public List<CategoryDao> getCategoryAndProduct() {
        return repo.getCategoryAndProduct();
    }
}