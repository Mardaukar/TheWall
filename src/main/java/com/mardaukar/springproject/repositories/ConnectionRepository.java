package com.mardaukar.springproject.repositories;

import com.mardaukar.springproject.entities.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

}