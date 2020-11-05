package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByVendor(Vendor vendor);

    @Query(value = "select p.* " +
            " from scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, products p, vendors v " +
            " where o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.delivery_date>=:fromDate) " +
            "           and (:toDate is null or o.delivery_date<=:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            " group by p.id " +
            " having 1=1 " +
            " and (:minCost is null or sum(i.quantity*i.rate)>:minCost) " +
            " and (:maxCost is null or sum(i.quantity*i.rate)<:maxCost) "
            , nativeQuery = true)
    public List<Product> findProductByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                    @Param("toDate") Timestamp toDate,
                                                    @Param("vendorId") String vendorId,
                                                    @Param("minCost") String minCost,
                                                    @Param("maxCost") String maxCost);

    @Query(value = "SELECT count(distinct o.id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, products p, vendors v " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.delivery_date>=:fromDate) " +
            "           and (:toDate is null or o.delivery_date<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY p.id "
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithoutGroupBy(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(YEAR FROM o.delivery_date), count(distinct p.id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v  " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.delivery_date>=:fromDate) " +
            "           and (:toDate is null or o.delivery_date<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY EXTRACT(YEAR FROM o.delivery_date)"
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByYear(@Param("fromDate") Timestamp fromDate,
                                                                    @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.delivery_date, '%Y %b') as month, count(distinct p.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.delivery_date>=:fromDate) " +
            "           and (:toDate is null or o.delivery_date<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY month "
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByYearMonth(@Param("fromDate") Timestamp fromDate,
                                                                         @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT DATE_FORMAT(o.delivery_date, '%Y %b %e') as week , count(distinct p.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.delivery_date>=:fromDate) " +
            "           and (:toDate is null or o.delivery_date<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY week "
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByWeek(@Param("fromDate") Timestamp fromDate,
                                                                    @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

    @Query(value = "SELECT EXTRACT(DAY FROM o.delivery_date), count(distinct p.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.delivery_date>=:fromDate) " +
            "           and (:toDate is null or o.delivery_date<=:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY EXTRACT(DAY FROM o.delivery_date)"
            , nativeQuery = true)
    public List<Object[]> findProductByReportRequestWithGroupByDay(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate, @Param("vendorId") String vendorId);

}
