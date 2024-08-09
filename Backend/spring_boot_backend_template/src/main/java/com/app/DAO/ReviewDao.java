package com.app.DAO;

import com.app.entities.Registeration.Registration;
import com.app.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDao extends JpaRepository<Review,Long> {
}
