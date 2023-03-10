package com.ecommerce.admin.controller;

import com.commerce.library.model.Category;
import com.commerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("size",categories.size());
        model.addAttribute("title","Category");
//        after adding request new obj @ModelAttribute
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes){
        try{
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Successfully add to the Category");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Category Name already exist");
        }
        catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error");
        }

//        after added back to categories page
        return "redirect:/categories";
    }
}
