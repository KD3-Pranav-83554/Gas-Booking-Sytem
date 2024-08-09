package com.app.DAO;

import com.app.entities.Agency;
import com.app.entities.Registeration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyDao extends JpaRepository<Agency,Long> {
}
