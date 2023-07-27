package com.mardaukar.springproject.controllers;

import com.mardaukar.springproject.entities.Profile;
import com.mardaukar.springproject.repositories.ProfileRepository;
import com.mardaukar.springproject.services.ConnectionsService;
import com.mardaukar.springproject.services.ProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConnectionsController {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private ConnectionsService connectionsService;
    
    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/connections")
    public String connections(Model model) {
        List<List<Profile>> relatedProfiles = connectionsService.getRelatedProfiles();
        model.addAttribute("requestingProfiles", relatedProfiles.get(0));
        model.addAttribute("requestedProfiles", relatedProfiles.get(1));
        model.addAttribute("connectedProfiles", relatedProfiles.get(2));
        return "connections";
    }
    
    @PostMapping("/request")
    public String requestConnection(@RequestParam String targetUsername) {
        Profile targetProfile = profileService.getProfileByUsername(targetUsername);
        connectionsService.createNewRequest(targetProfile);
        return "redirect:/profiles/" + targetProfile.getProfileString();
    }
    
    @PostMapping("/remove")
    public String removeConnection(@RequestParam String targetUsername, @RequestParam Boolean connectionsPage) {
        Profile targetProfile = profileService.getProfileByUsername(targetUsername);
        connectionsService.removeConnection(targetProfile);
 
        if (connectionsPage == true) {
            return "redirect:/connections";
        } else {
            return "redirect:/profiles/" + targetProfile.getProfileString();
        }
    }
    
    @PostMapping("/cancel")
    public String cancelRequest(@RequestParam String targetUsername, @RequestParam Boolean connectionsPage) {
        Profile targetProfile = profileService.getProfileByUsername(targetUsername);
        connectionsService.cancelRequest(targetProfile);
        
        if (connectionsPage == true) {
            return "redirect:/connections";
        } else {
            return "redirect:/profiles/" + targetProfile.getProfileString();
        }
    }
    
    @PostMapping("/decline")
    public String declineRequest(@RequestParam String requestorUsername, @RequestParam Boolean connectionsPage) {
        Profile targetProfile = profileService.getProfileByUsername(requestorUsername);
        connectionsService.declineRequest(targetProfile);
        
        if (connectionsPage == true) {
            return "redirect:/connections";
        } else {
            return "redirect:/profiles/" + targetProfile.getProfileString();
        }
    }
    
    @PostMapping("/accept")
    public String acceptRequest(@RequestParam String requestorUsername, @RequestParam Boolean connectionsPage) {
        Profile targetProfile = profileRepository.findByUsername(requestorUsername);
        connectionsService.acceptRequest(targetProfile);
        
        if (connectionsPage == true) {
            return "redirect:/connections";
        } else {
            return "redirect:/profiles/" + targetProfile.getProfileString();
        }
    }
}