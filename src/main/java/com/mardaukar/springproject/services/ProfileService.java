
package com.mardaukar.springproject.services;

import com.mardaukar.springproject.entities.Connection;
import com.mardaukar.springproject.entities.Profile;
import com.mardaukar.springproject.entities.Request;
import com.mardaukar.springproject.repositories.ConnectionRepository;
import com.mardaukar.springproject.repositories.ProfileRepository;
import com.mardaukar.springproject.repositories.RequestRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private ConnectionRepository connectionRepository;
    
    @Autowired
    private RequestRepository requestRepository;
    
    public List<Profile> getSearchProfiles(String searchName) {
        List<Profile> profiles = profileRepository.findAll();
        List<Profile> searchProfiles = new ArrayList<>();
        
        for (Profile profile : profiles) {
            if (profile.getName().toLowerCase().contains(searchName.toLowerCase())) {
                searchProfiles.add(profile);
            }
        }
        
        return searchProfiles;
    }
    
    public String getUserProfileString() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile p = profileRepository.findByUsername(username);
        String ps = p.getProfileString();
        return ps;
    }
    
    public void addNewSkill(String skill) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile p = profileRepository.findByUsername(username);
        p.getSkills().add(skill);
        profileRepository.save(p);
    }
    
    public Profile getProfileByProfileString(String profileString) {
        Profile p = profileRepository.findByProfileString(profileString);
        return p;
    }
    
    public Profile getProfileByUsername(String username) {
        Profile p = profileRepository.findByUsername(username);
        return p;
    }
    
    public String getPageProfileUserConnectionStatus(Profile pageProfile) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);
        
        if(pageProfile.getUsername().equals(username)) {
            return "user";
        }
        
        List<Connection> connections = connectionRepository.findAll();
        for (Connection connection: connections) {
            if (connection.getProfiles().contains(userProfile)) {
                if (connection.getProfiles().contains(pageProfile)) {
                    return "connected";
                }
            }
        }
        
        List<Request> receivedRequests = requestRepository.findByReceiver(userProfile);
        for (Request request : receivedRequests) {
            if (request.getRequestor().equals(pageProfile)) {
                return "request_received";
            }
        }
        
        List<Request> sentRequests = requestRepository.findByRequestor(userProfile);
        for (Request request : sentRequests) {
            if (request.getReceiver().equals(pageProfile)) {
                return "request_sent";
            }
        }     
        
        return "not_connected";
    }
    
}
