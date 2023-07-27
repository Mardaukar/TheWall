package com.mardaukar.springproject.repositories;

import com.mardaukar.springproject.entities.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByOrderByPostedDesc();
}