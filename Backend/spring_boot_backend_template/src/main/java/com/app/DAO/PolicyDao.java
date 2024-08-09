package com.app.DAO;

import com.app.entities.Consumer.Consumer;
import com.app.entities.Policy.CancellationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyDao extends JpaRepository<CancellationPolicy,Long> {
}
