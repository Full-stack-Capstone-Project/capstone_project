package com.commerce.library.service;

import com.commerce.library.dao.AdminDao;
import com.commerce.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDao adminDao);
}
