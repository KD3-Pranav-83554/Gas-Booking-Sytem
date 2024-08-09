package com.app.DAO;

import com.app.entities.Consumer.Consumer;
import com.app.entities.Payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDao extends JpaRepository<Payment,Long> {
}
