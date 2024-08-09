package com.app.DAO;

import com.app.entities.Registeration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterationDao extends JpaRepository<Registration,Long> {
}
