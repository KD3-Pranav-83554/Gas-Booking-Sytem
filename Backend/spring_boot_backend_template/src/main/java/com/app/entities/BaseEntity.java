package com.app.entities;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity
{

    //Created timestamp
    @CreationTimestamp
    @Column(name="created_on")
    private LocalDate createdOn;


    //Updated timestamp
    @UpdateTimestamp
    @Column(name="updated_on")
    private LocalDateTime updatedOn;
}
