package com.ecommerce.admin.controller;

import com.commerce.library.dao.AdminDao;
import com.commerce.library.model.Admin;
import com.commerce.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class LoginController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("title","Login");
        return "login";
    }

    @RequestMapping("/index")
    public String home(Model model){
        model.addAttribute("title","Home Page");
//        这段代码主要用于检查用户是否已经通过身份验证并登录，如果用户没有通过身份验证或尚未登录，则重定向到登录页面。
//        AnonymousAuthenticationToken 是 Spring Security 中的一个类，用于表示匿名用户的身份验证信息。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title","Register");
        model.addAttribute("adminDao",new AdminDao());
        return "register";
    }

    @GetMapping("/forgotPassword")
    public String forgotPW(Model model){

        model.addAttribute("title","Forgot Password");
        return "forgotPassword";
    }

    /**
     *
     * @param adminDao same name from register(Model model){}
     * @param result AdminDao wrong input (like Reg)
     * @param model model
     * @return
     *
     * hash and save password
     */
    @PostMapping("/new-register")
    //@ModelAttribute("adminDao") same name from register(Model model){}
    public String addNewAdmin(@Valid @ModelAttribute("adminDao")AdminDao adminDao, BindingResult result, Model model){
        try {
            //remove exist message
//            session.removeAttribute("message");
            //wrong input
            if(result.hasErrors()){
                model.addAttribute("adminDao",adminDao);
                return "register";
            }
            //already exists
            String username = adminDao.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null){
                model.addAttribute("adminDao",adminDao);
//                session.setAttribute("message","User already exists！");
                model.addAttribute("emailError","User already exists！");
                return "register";
            }
            //check password
            if(adminDao.getPassword().equals(adminDao.getRepeatPassword())){
                adminDao.setPassword(passwordEncoder.encode(adminDao.getPassword()));
                adminService.save(adminDao);
//                session.setAttribute("message","Welcome, registration success!");
                model.addAttribute("success","Welcome, registration success!");

                model.addAttribute("adminDao",adminDao);
            }else {
                model.addAttribute("adminDao",adminDao);
//                session.setAttribute("message","Password not same!");
                model.addAttribute("passwordError","Passwords do not match! Please try again!");
                System.out.println("PW not same");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
//            session.setAttribute("message","Server maintenance, please try again later! Thank you!");
            model.addAttribute("errors","Server maintenance, please try again later! Thank you!");

        }
        return "register";
    }

}
