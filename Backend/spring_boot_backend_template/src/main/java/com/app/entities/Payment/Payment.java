package com.app.entities.Payment;

import com.app.entities.Consumer.Consumer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;




@Entity
@Data
@ToString(exclude = "consumer")
@Table(name="Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    Long paymentId;

    @Column(name="payment_type")
    PaymentType type;

    @ManyToOne
    @JoinColumn(name="consumer_id",nullable = false)
    Consumer consumer;

}
