package com.app.entities.Consumer;


import com.app.entities.Agency;
import com.app.entities.BaseEntity;
import com.app.entities.Order.Order;
import com.app.entities.Payment.Payment;
import com.app.entities.Registeration.Registration;
import com.app.entities.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Consumer")
@Getter
@Setter
@NoArgsConstructor
//To avoid printing all the composite entities and to avoid recursion in case of bidirectional association
@ToString(exclude = {"registration","orders","reviews","paymentMethods"},callSuper = true)
public class Consumer extends BaseEntity {
    @Id
    @Column(name="consumer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long consumerId;

    //FirstName and LastName
    @Column(name="first_name")
    String firstName;
    @Column(name="last_name")
    String lastName;

    @Column(name="address")
    String address;

    //Email&Pass

    @Column(unique = true,length=25)
    String email;
    @Column(length=25,nullable = false)
    String password;
    /************************************/



    @Enumerated(EnumType.STRING)
    @Column(name="consumer_type")
    ConsumerType consumerType;

    LocalDate DoB;


    @Column(name="phone_no",unique = true)
    Integer phoneNo;



    /***************Document Verification***********************************/
    @Column(name="document_name")
    String verificationDocName;
    @Column(name="document_type")
    String verificationDocType;

    //Storing image in a BLOB
    @Lob
    Byte[] verificationDoc;


    /*********************************************/




    //ConnectionCount
    @Column(name="connection_count")
    Integer connectionCount;




    //If consumer gets modified then changes has to be reflected in child entities
    @OneToOne(mappedBy = "consumer",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    Registration registeration;


    //AnyToMany default fetching policy LAZY.
    @OneToMany(mappedBy = "consumer" ,cascade = CascadeType.ALL,orphanRemoval = true)
    List<Payment> paymentMethods=new ArrayList<>();

    @OneToMany(mappedBy = "consumer",cascade = CascadeType.ALL,orphanRemoval = true)
    List<Order> orders=new ArrayList<>();




    //By Default AnyToOne Eager behaviour to avoid the recursion while printing toString set fetch type to LAZY
    @ManyToOne
    @JoinColumn(name="agencyId")
    Agency agency;



    //Pending set the agency and set consumer to agency


}
