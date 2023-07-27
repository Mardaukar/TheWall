package com.mardaukar.springproject.controllers;

import com.mardaukar.springproject.entities.Post;
import com.mardaukar.springproject.services.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WallController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/wall")
    public String wall(Model model) {
        List<Post> wallPosts = postService.getWallPosts();
        model.addAttribute("posts", wallPosts);
        return "wall";
    }
    
    @PostMapping("/wall")
    public String post(@RequestParam String post) {
        postService.createNewPost(post);   
        return "redirect:/wall";
    }
    
}
