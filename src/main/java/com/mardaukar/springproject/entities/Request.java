package com.mardaukar.springproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request extends AbstractPersistable<Long> {

    @ManyToOne
    private Profile requestor;
    @ManyToOne
    private Profile receiver;

}