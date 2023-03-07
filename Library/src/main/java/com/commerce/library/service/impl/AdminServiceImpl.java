package com.commerce.library.service.impl;

import com.commerce.library.dao.AdminDao;
import com.commerce.library.model.Admin;
import com.commerce.library.repository.AdminRepository;
import com.commerce.library.repository.RoleRepository;
import com.commerce.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDao adminDao) {
        Admin admin = new Admin();
        admin.setFirstname(adminDao.getFirstName());
        admin.setLastname(adminDao.getLastName());
        admin.setUsername(adminDao.getUsername());
        admin.setPassword(adminDao.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }
}