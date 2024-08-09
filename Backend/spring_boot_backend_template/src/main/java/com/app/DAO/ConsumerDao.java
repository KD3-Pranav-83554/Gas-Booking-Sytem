package com.app.DAO;

import com.app.entities.Consumer.Consumer;
import com.app.entities.Order.Order;
import com.app.entities.Payment.Payment;
import com.app.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ConsumerDao extends JpaRepository<Consumer,Long> {
    Optional<Consumer> findByEmail(String email);
    Consumer findByEmailAndPassword(String email, String password);

    @Query("select c from Consumer c left join fetch c.orders ")
    List<Consumer> getAllConsumerWithOrders();


    @Query("select c from Consumer c left join fetch c.orders where c.consumerId=:id ")
    Optional<Consumer> getConsumerWithOrders(@Param("id") Long id);

    @Query("select c from Consumer c left join fetch c.paymentMethods ")
    List<Consumer> getAllConsumerWithPayment();

    @Query("select c from Consumer c left join fetch c.paymentMethods where c.consumerId=:id ")
    Optional<Consumer> getConsumerWithPayment(@Param("id") Long id);



}
