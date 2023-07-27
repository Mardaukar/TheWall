package com.mardaukar.springproject.controllers;

import com.mardaukar.springproject.entities.Profile;
import com.mardaukar.springproject.repositories.ProfileRepository;
import com.mardaukar.springproject.services.ProfileService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    
    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/search")
    public String search() {
        return "search";
    }
    
    @PostMapping("/search")
    public String list(@RequestParam String name, Model model) {      
        List<Profile> searchProfiles = profileService.getSearchProfiles(name);
        model.addAttribute("profiles", searchProfiles);
        model.addAttribute("searchMade", true);
        return "search";
    }
    
}
