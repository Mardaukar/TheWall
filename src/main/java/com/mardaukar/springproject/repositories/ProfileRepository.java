package com.mardaukar.springproject.repositories;

import com.mardaukar.springproject.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUsername(String username);
    Profile findByProfileString(String profileString);
}