package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = " select count(distinct o.user_id) " +
            " from orders o, orders_items oi, order_items i, products p, vendors v " +
            " where o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and  p.vendor_id=v.id and (:vendorId is null or v.id=:vendorId) group by v.id ", nativeQuery = true)
    public Long countUserByVendorId(@Param("vendorId") String vendorId);

}
