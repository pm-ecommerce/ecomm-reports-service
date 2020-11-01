package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
