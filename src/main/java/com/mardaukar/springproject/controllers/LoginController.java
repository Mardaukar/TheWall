package com.mardaukar.springproject.controllers;

import com.mardaukar.springproject.entities.Profile;
import com.mardaukar.springproject.repositories.ProfileRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    
    @GetMapping("/login")
    public String customLogin() {
        return "login";
    }

    @PostMapping("/register")
    public String createNew(@RequestParam String name, @RequestParam String username, @RequestParam String profileString, @RequestParam String password, Model model) {
        if (profileRepository.findByUsername(username) != null) {
            model.addAttribute("username_exists", true);
            return "register";
        }
        if (profileRepository.findByProfileString(profileString) != null) {
            model.addAttribute("profilestring_exists", true);
            return "register";
        }
        
        Profile p = new Profile(name, username, profileString, passwordEncoder.encode(password), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "", new ArrayList<>());
        profileRepository.save(p);
        return "redirect:/login";
    }
}