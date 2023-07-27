package com.mardaukar.springproject.controllers;

import com.mardaukar.springproject.entities.Profile;
import com.mardaukar.springproject.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/my_profile")
    public String myProfile() {
        String ps = profileService.getUserProfileString(); 
        return "redirect:/profiles/" + ps;
    }
    
    @GetMapping("/profile")
    public String profile(@RequestParam String targetProfileString) {   
        return "redirect:/profiles/" + targetProfileString;
    }
    
    @GetMapping("/profiles/{profileString}")
    public String getProfile(Model model, @PathVariable String profileString) {
        Profile pageProfile = profileService.getProfileByProfileString(profileString);
        model.addAttribute("profile", pageProfile);
        
        String status = profileService.getPageProfileUserConnectionStatus(pageProfile);
        model.addAttribute("status", status);
        
        return "profile";
    }
    
    @PostMapping("/skill")
    public String skill(@RequestParam String skill) {
        profileService.addNewSkill(skill);
        return "redirect:/my_profile";
    }
}