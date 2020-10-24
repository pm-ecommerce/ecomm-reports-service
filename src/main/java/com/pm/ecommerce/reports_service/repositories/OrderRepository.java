package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value="select distinct * from orders o, orders_items osi, order_items oi, products p, vendors v " +
            " where o.id=osi.order_id and osi.items_id=oi.id and oi.product_id=p.id and p.vendor_id=v.id " +
            "   and (:fromDate is null or o.created_date>:fromDate) " +
            "   and (:toDate is null or o.created_date<:toDate) " +
            "   and (:minPrice is null or p.price>:minPrice) " +
            "   and (:maxPrice is null or p.price<:maxPrice) " +
            "   and (:vendorId is null or v.id=:vendorId) " +
            "   and o.id in " +
            "            (" +
            "               select distinct osi.order_id " +
            "               from orders o, orders_items osi, order_items oi, products p" +
            "               where o.id=osi.order_id and osi.items_id=oi.id and oi.product_id=p.id " +
            "               group by osi.order_id " +
            "               having 1=1 " +
            "                   and (:minCost is null or sum(oi.quantity*p.price)>:minCost) " +
            "                   and (:maxCost is null or sum(oi.quantity*p.price)<:maxCost) " +
            "            )"
            ,nativeQuery=true)
    public List<Order> findOrderByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                @Param("toDate") Timestamp toDate,
                                                @Param("vendorId") String vendorId,
                                                @Param("minPrice") String minPrice,
                                                @Param("maxPrice") String maxPrice,
                                                @Param("minCost") String minCost,
                                                @Param("maxCost") String maxCost);
}
