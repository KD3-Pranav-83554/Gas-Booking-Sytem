package com.app.DTO;


import com.app.entities.Consumer.ConsumerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;


//For Registeration
@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDTO {
    private Long consumerId;


    String email;
    String password;
    String address;
    String consumerType;
    String DoB;
    Integer phoneNo;

    //Pending verification Document
}
