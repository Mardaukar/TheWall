package com.mardaukar.springproject.repositories;

import com.mardaukar.springproject.entities.Profile;
import com.mardaukar.springproject.entities.Request;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequestor(Profile requestor);
    List<Request> findByReceiver(Profile receiver);
    Request findByRequestorAndReceiver(Profile requestor, Profile receiver);
}