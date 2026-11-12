package com.lesson.memo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/signin")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/admin/signup")
    public String register() {
        return "admin/register";
    }
 

    @PostMapping("/admin/signup")
    public String create(
            @RequestParam String lastName,
            @RequestParam String firstName,
            @RequestParam String email,
            @RequestParam String password) {

        if (adminRepository.findByEmail(email).isPresent()) {
        	   return "redirect:/admin/signup?error";
        }

        Admin admin = new Admin();
        admin.setLastName(lastName);
        admin.setFirstName(firstName);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));

        adminRepository.save(admin);

        return "redirect:/admin/signin?registered";
    }
}