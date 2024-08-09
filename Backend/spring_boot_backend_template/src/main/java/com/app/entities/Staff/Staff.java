package com.app.entities.Staff;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Staff")
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staff_id")
    Long StaffId;



    @Column(name="first_name")
    String firstName;
    @Column(name="last_name")
    String lastName;


    String address;

    StaffRole role;
}
