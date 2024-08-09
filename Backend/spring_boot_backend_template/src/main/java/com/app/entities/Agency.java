package com.app.entities;


import com.app.entities.Consumer.Consumer;
import com.app.entities.Order.Order;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="agency")
@Data
@ToString(exclude={"orders","consumers"})
public class Agency extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="agency_id")
    Long AgencyId;

    @Column(name="agency_name")
    String agencyName;

    @Column(name="stock")
    Integer stock;

    @Column(name="phone_no")
    Long PhoneNo;


    @Column(name="address_id")
    String agencyAddress;

    @Column(name="vendor_name",length=40)
    String vendorName;

    @OneToMany(mappedBy = "agency",cascade =CascadeType.ALL,orphanRemoval = true)
    List<Consumer> consumers;

    @OneToMany(mappedBy = "agency",cascade = CascadeType.ALL,orphanRemoval = true)
    List<Order> orders;

}
