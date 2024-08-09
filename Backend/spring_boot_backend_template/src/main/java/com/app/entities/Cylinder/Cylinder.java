package com.app.entities.Cylinder;

import com.app.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Cylinder")
@ToString(callSuper = true)
public class Cylinder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cylinder_id")
    Long cylinderId;


    @Enumerated(EnumType.STRING)
    @Column(name="cylinder_type")
    CylinderType cylinderType;
    Double price;
    Double Weight;
}
