package com.app.entities.Registeration;

import com.app.entities.Consumer.Consumer;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="registeration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="registeration_id")
    Long registrationId;


    @Enumerated(EnumType.STRING)
    RegistrationStatus status;


    @Column(length = 250)
    String remark;

    @OneToOne
    @JoinColumn(name="consumer_id")
    Consumer consumer;

}
