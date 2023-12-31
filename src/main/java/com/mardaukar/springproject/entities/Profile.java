package com.mardaukar.springproject.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends AbstractPersistable<Long> {

    private String name;
    private String username;
    private String profileString;
    private String password;
    @OneToMany(mappedBy = "poster")
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "requestor")
    private List<Request> sentRequests = new ArrayList<>();
    @OneToMany(mappedBy = "receiver")
    private List<Request> receivedRequests = new ArrayList<>();
    private String status;
    @ElementCollection(targetClass=String.class)
    private List<String> skills = new ArrayList<>();
}