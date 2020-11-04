package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    @Query(value = "select v.* " +
            " from scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, products p, vendors v " +
            " where o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "     and (:fromDate is null or o.delivery_date>:fromDate) " +
            "     and (:toDate is null or o.delivery_date<:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) " +
            " group by v.id " +
            " having 1=1 " +
            " and (:minCost is null or sum(i.quantity*i.rate)>:minCost) " +
            " and (:maxCost is null or sum(i.quantity*i.rate)<:maxCost) "
            , nativeQuery = true)
    public List<Vendor> findVendorByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                  @Param("toDate") Timestamp toDate,
                                                  @Param("vendorId") String vendorId,
                                                  @Param("minCost") String minCost,
                                                  @Param("maxCost") String maxCost);

    @Query(value = "SELECT count(distinct o.id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY v.id "
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithoutGroupBy(@Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(YEAR FROM o.delivery_date), count(distinct v.id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v  " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY EXTRACT(YEAR FROM o.delivery_date)"
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByYear(@Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.delivery_date, '%Y %b') as month, count(distinct v.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY month "
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByYearMonth(@Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.delivery_date, '%Y %b %e') as week , count(distinct v.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY week "
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByWeek(@Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(DAY FROM o.delivery_date), count(distinct v.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY EXTRACT(DAY FROM o.delivery_date)"
            , nativeQuery = true)
    public List<Object[]> findVendorByReportRequestWithGroupByDay(@Param("vendorId") String vendorId);

}
