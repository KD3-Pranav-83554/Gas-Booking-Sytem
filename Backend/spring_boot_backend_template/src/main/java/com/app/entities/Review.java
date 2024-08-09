package com.app.entities;

import com.app.entities.Consumer.Consumer;
import com.app.entities.Order.Order;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="review")
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    Long reviewId;


    @Column(name="review_points")
    Double reviewPoints;


    @Column(name="review_text",length=300)
    String reviewText;


    @OneToOne
    @JoinColumn(name="order_id")
    Order order;



}
