package com.mardaukar.springproject.services;

import com.mardaukar.springproject.entities.Connection;
import com.mardaukar.springproject.entities.Post;
import com.mardaukar.springproject.entities.Profile;
import com.mardaukar.springproject.repositories.ConnectionRepository;
import com.mardaukar.springproject.repositories.PostRepository;
import com.mardaukar.springproject.repositories.ProfileRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private ConnectionRepository connectionRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    public void createNewPost(String post) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile p = profileRepository.findByUsername(username);
        LocalDateTime time = LocalDateTime.now();
        time.truncatedTo(ChronoUnit.SECONDS);
        
        postRepository.save(new Post(p, post, time));
    }
    
    public List<Post> getWallPosts() {
        List<Post> posts = postRepository.findAllByOrderByPostedDesc();
        List<Connection> connections = connectionRepository.findAll();
        List<Post> connectedPosts = new ArrayList<>();
        List<Profile> connectedProfiles = new ArrayList<>();
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile userProfile = profileRepository.findByUsername(username);
        
        for (Connection connection: connections) {
            if (connection.getProfiles().contains(userProfile)) {
                for (Profile profile : connection.getProfiles()) {
                    if (!profile.equals(userProfile)) {
                        connectedProfiles.add(profile);
                    }
                }
            }
        }

        for (Post post : posts) {
            if (connectedProfiles.contains(post.getPoster()) || post.getPoster().equals(userProfile)) {
                connectedPosts.add(post);
            }
        }
        
        if (connectedPosts.size() < 25) {
            return connectedPosts;
        } else {
            return connectedPosts.subList(0, 25);
        }
    }
    
}
