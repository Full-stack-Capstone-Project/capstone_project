package com.ecommerce.admin.controller;

import com.commerce.library.model.Category;
import com.commerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> 58959b96817a80fd153dc69b31cbe4da94c6cbfa
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
<<<<<<< HEAD

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes){
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success","Updated successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Name already exists, please try again");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Error");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes){
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Category deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Failed to deleted");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes attributes){
        try {
            categoryService.enabledById(id);
            attributes.addFlashAttribute("success", "Category enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/categories";
    }
=======
>>>>>>> 58959b96817a80fd153dc69b31cbe4da94c6cbfa
}
