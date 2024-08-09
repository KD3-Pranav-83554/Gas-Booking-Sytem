package com.app.DAO;

import com.app.entities.Registeration.Registration;
import com.app.entities.Staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao extends JpaRepository<Staff,Long> {
}
