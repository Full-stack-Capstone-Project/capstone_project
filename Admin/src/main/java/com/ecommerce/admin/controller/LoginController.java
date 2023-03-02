package com.ecommerce.admin.controller;

import com.commerce.library.dao.AdminDao;
import com.commerce.library.model.Admin;
import com.commerce.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String loginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("adminDao",new AdminDao());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPW(Model model){
        return "forgot-password";
    }

    /**
     *
     * @param adminDao same name from register(Model model){}
     * @param result AdminDao wrong input (like Reg)
     * @param model model
     * @param session message
     * @return
     *
     * hash and save password
     */
    @PostMapping("/new-register")
    //@ModelAttribute("adminDao") same name from register(Model model){}
    public String addNewAdmin(@Valid @ModelAttribute("adminDao")AdminDao adminDao, BindingResult result, Model model, HttpSession session){
        try {
            //remove exist message
            session.removeAttribute("message");
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
                session.setAttribute("message","User already exists！");
                return "register";
            }
            //check password
            if(adminDao.getPassword().equals(adminDao.getRepeatPassword())){
                adminDao.setPassword(passwordEncoder.encode(adminDao.getPassword()));
                adminService.save(adminDao);
                session.setAttribute("message","Welcome, registration success!");
                model.addAttribute("adminDao",adminDao);
            }else {
                model.addAttribute("adminDao",adminDao);
                session.setAttribute("message","Password not same!");
                System.out.println("PW not same");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("message","Server maintenance, please try again later! Thank you!");
            return "register";

        }
        return "register";
    }

}
