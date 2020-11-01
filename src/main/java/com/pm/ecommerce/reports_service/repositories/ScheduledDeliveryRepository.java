package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledDeliveryRepository extends JpaRepository<ScheduledDelivery,Integer> {
    List<ScheduledDelivery> findAllByVendor(Vendor vendor);
}
