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
public class ConnectionsService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private RequestRepository requestRepository;
    
    @Autowired
    private ConnectionRepository connectionRepository;
    
    public void createNewRequest(Profile targetProfile) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);      
        requestRepository.save(new Request(userProfile, targetProfile));
    }
    
    public void removeConnection(Profile targetProfile) {   
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);
        
        List<Connection> connections = connectionRepository.findAll();
        for (Connection connection: connections) {
            if (connection.getProfiles().contains(userProfile)) {
                if (connection.getProfiles().contains(targetProfile)) {
                    connectionRepository.delete(connection);
                }
            }
        }
    }
    
    public void cancelRequest(Profile targetProfile) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);
        Request r = requestRepository.findByRequestorAndReceiver(userProfile, targetProfile);
        requestRepository.delete(r);
    }
    
    public void declineRequest(Profile targetProfile) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);
        Request r = requestRepository.findByRequestorAndReceiver(targetProfile, userProfile);
        requestRepository.delete(r);
    }
    
    public void acceptRequest(Profile targetProfile) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);
        Request r = requestRepository.findByRequestorAndReceiver(targetProfile, userProfile);
        requestRepository.delete(r);
        
        Connection connection = new Connection(new ArrayList<>());
        connection.getProfiles().add(userProfile);
        connection.getProfiles().add(targetProfile);
        connectionRepository.save(connection);
    }
    
    public List<List<Profile>> getRelatedProfiles() {
        List<Profile> requestingProfiles = new ArrayList<>();
        List<Profile> requestedProfiles = new ArrayList<>();
        List<Profile> connectedProfiles = new ArrayList<>();
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);
        
        List<Request> sentRequests = requestRepository.findByRequestor(userProfile);
        for (Request request : sentRequests) {
            requestedProfiles.add(request.getReceiver());
        }
        
        List<Request> receivedRequests = requestRepository.findByReceiver(userProfile);
        for (Request request : receivedRequests) {
            requestingProfiles.add(request.getRequestor());
        }
        
        List<Connection> connections = connectionRepository.findAll();
        for (Connection connection: connections) {
            if (connection.getProfiles().contains(userProfile)) {
                for (Profile profile : connection.getProfiles()) {
                    if (!profile.equals(userProfile)) {
                        connectedProfiles.add(profile);
                    }
                }
            }
        }
        
        List<List<Profile>> relatedProfiles = new ArrayList<>();
        relatedProfiles.add(requestingProfiles);
        relatedProfiles.add(requestedProfiles);
        relatedProfiles.add(connectedProfiles);
        
        return relatedProfiles;
    }
    
}
