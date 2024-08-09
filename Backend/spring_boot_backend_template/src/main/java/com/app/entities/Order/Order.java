package com.app.entities.Order;

import com.app.entities.Agency;
import com.app.entities.BaseEntity;
import com.app.entities.Policy.CancellationPolicy;
import com.app.entities.Consumer.Consumer;
import com.app.entities.Cylinder.Cylinder;
import com.app.entities.Payment.Payment;
import com.app.entities.Review;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name="order_table")
@ToString(exclude = {"consumer","payment","agency"},callSuper = true)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    Long orderId;


    @ManyToOne
    @JoinColumn(name="consumer_id",nullable = false)
    Consumer consumer;


    //By Default eager and keeping it eager -> No associations in cylinder entity
    @OneToMany(cascade=CascadeType.ALL,orphanRemoval = true)
    List<Cylinder> cylinders;

    @Column(name="order_total")
    Double orderTotal;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status")
    OrderStatus orderStatus;




    @Column(name="refunded_amount")
    Double refundedAmt;

    //From consumer
    @Column(name="consumer_order_instruction")
    String orderInstruction;

    //From admin
    @Column(name="admin_order_reply")
    String orderReply;

    @Column(name="order_date")
    LocalDate orderDate;

    @Column(name="expected_delivery_date")
    LocalDate ExpectedDeliveryDate;



    @Column(name="reason_for_cancellation",length = 255)
    String reasonForCancellation;


    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    CancellationPolicy cancellationPolicy;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    Review review;


    @OneToOne
    Payment payment;


    @ManyToOne
    @JoinColumn(name="agency_id")
    Agency agency;
}
