package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = " select count(distinct c.id) " +
            "from categories c, products p, vendors v " +
            "where c.id = p.category_id and p.vendor_id=v.id and (:vendorId is null or v.id=:vendorId) ", nativeQuery = true)
    public Long countCategoriesByVendorId(@Param("vendorId") String vendorId);

    @Query(value="select c.* " +
            " from scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, products p, vendors v, categories c " +
            " where o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id " +
            "     and (:fromDate is null or o.created_date>:fromDate) " +
            "     and (:toDate is null or o.created_date<:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) and" +
            " group by c.id and o.status <= 3" +
            " having 1=1 " +
            " and (:minCost is null or sum(i.quantity*i.rate)>:minCost) " +
            " and (:maxCost is null or sum(i.quantity*i.rate)<:maxCost) "
    , nativeQuery=true)
    public List<Category> findCategoryByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                @Param("toDate") Timestamp toDate,
                                                @Param("vendorId") String vendorId,
                                                @Param("minCost") String minCost,
                                                @Param("maxCost") String maxCost);

    @Query(value="SELECT count(distinct o.id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, products p, vendors v, categories c " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.scheduled_delivery_id=i.id and i.product_id=p.id and o.vendor_id=v.id and p.category_id=c.id  and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY c.id "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithoutGroupBy(@Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(YEAR FROM o.delivery_date), count(distinct c.id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v, categories c   " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and o.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY EXTRACT(YEAR FROM o.delivery_date)"
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByYear(@Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.delivery_date, '%Y %b') as month, count(distinct c.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v, categories c  " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and o.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY month "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByYearMonth(@Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.delivery_date, '%Y %b %e') as week, count(distinct c.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v, categories c  " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY week "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByWeek(@Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(DAY FROM o.delivery_date), count(distinct c.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, products p, vendors v, categories c  " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "    GROUP BY EXTRACT(DAY FROM o.delivery_date)"
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByDay(@Param("vendorId") String vendorId);

}
