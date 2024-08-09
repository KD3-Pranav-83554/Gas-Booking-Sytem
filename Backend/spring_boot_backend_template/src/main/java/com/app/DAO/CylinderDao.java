package com.app.DAO;

import com.app.entities.Consumer.Consumer;
import com.app.entities.Cylinder.Cylinder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CylinderDao extends JpaRepository<Cylinder,Long> {
}
