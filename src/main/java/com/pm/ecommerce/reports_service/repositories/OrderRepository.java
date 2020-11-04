package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.entities.ScheduledDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<ScheduledDelivery,Integer> {

    @Query(value="select o.* " +
            " from scheduled_deliveries o, scheduled_deliveries_items oi, order_items i,  vendors v " +
            " where o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
                    "     and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            " group by o.id "
            , nativeQuery=true)
    public List<ScheduledDelivery> findOrdersByVendor(@Param("vendorId") String vendorId);

    @Query(value="select o.* " +
            " from scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, vendors v " +
            " where o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "     and (o.delivery_date>=:fromDate) " +
            "     and (o.delivery_date<=:toDate) " +
            "     and o.status <= 3" +
            " group by o.id "
            , nativeQuery=true)
    public List<ScheduledDelivery> findOrderByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                @Param("toDate") Timestamp toDate);
    @Query(value="select o.* " +
            " from scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, vendors v " +
            " where o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "     and (:fromDate is null or o.delivery_date>:fromDate) " +
            "     and (:toDate is null or o.delivery_date<:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            " group by o.id "
            , nativeQuery=true)
    public List<ScheduledDelivery> findOrderByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                @Param("toDate") Timestamp toDate,
                                                @Param("vendorId") String vendorId);

    @Query(value="SELECT count(oi.order_id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3" +
            "     GROUP BY oi.scheduled_delivery_id "
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithoutGroupBy(@Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(YEAR FROM o.delivery_date), count(o.id), sum(i.quantity*i.rate) " +
            "     FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v  " +
            "     WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "     GROUP BY EXTRACT(YEAR FROM o.delivery_date)"
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByYear(@Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.delivery_date, '%Y %b') as month, count(o.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "    GROUP BY month "
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByYearMonth(@Param("vendorId") String vendorId);

    @Query(value="SELECT DATE_FORMAT(o.delivery_date, '%Y %b %e') as week, count(o.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "    GROUP BY week "
            ,nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByWeek(@Param("vendorId") String vendorId);

    @Query(value="SELECT EXTRACT(DAY FROM o.delivery_date), count(o.id), sum(i.quantity*i.rate) " +
            "    FROM scheduled_deliveries  o, scheduled_deliveries_items oi, order_items i, vendors v " +
            "    WHERE o.id=oi.scheduled_delivery_id and oi.items_id=i.id and o.vendor_id=v.id " +
            "           and (:vendorId is null or v.id=:vendorId) and o.status <= 3 " +
            "    GROUP BY EXTRACT(DAY FROM o.delivery_date)",nativeQuery=true)
    public List<Object[]> findOrdersByReportRequestWithGroupByDay(@Param("vendorId") String vendorId);

    @Query(value="SELECT count(*),a.state FROM orders o, addresses a WHERE o.shipping_address_id=a.id GROUP BY a.state",nativeQuery=true)
    public List<Object[]> findOrderBillingState();

    @Query(value="SELECT count(*),a.state FROM orders o, addresses a WHERE o.shipping_address_id=a.id GROUP BY a.state",nativeQuery=true)
    public List<Object[]> findOrderShippingState();

    @Query(value="SELECT count(o.id), EXTRACT(YEAR FROM o.created_date) as year FROM orders o GROUP BY year",nativeQuery=true)
    public List<Object[]> findOrderYear();

    @Query(value="SELECT count(o.id), DATE_FORMAT(o.created_date, '%Y %b') as month FROM orders o GROUP BY month",nativeQuery=true)
    public List<Object[]> findOrderYearMonth();

    @Query(value="SELECT count(distinct o.id), c.name " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v, categories c " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id " +
            "     GROUP BY c.id" +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderCategory();

    @Query(value="SELECT count(distinct o.id), v.business_name " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "     GROUP BY v.id" +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderVendor();

    @Query(value="SELECT count(distinct o.id), p.name " +
            "     FROM orders  o, orders_items oi, order_items i, products p " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id" +
            "     GROUP BY p.id " +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderProduct();

    //for vendor report

    @Query(value="SELECT count(distinct o.id) " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and v.id= :vendorId " +
            "     GROUP BY v.id "
            ,nativeQuery=true)
    public Integer findOrderTotalByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), c.name " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v, categories c " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.category_id=c.id and p.vendor_id=v.id and v.id= :vendorId " +
            "     GROUP BY c.id " +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10 "
            ,nativeQuery=true)
    public List<Object[]> findOrderByCategory(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), p.name " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and v.id=:vendorId " +
            "     GROUP BY p.id " +
            "     ORDER BY count(distinct o.id) DESC " +
            "     LIMIT 10",nativeQuery=true)
    public List<Object[]> findOrderProductByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), EXTRACT(YEAR FROM o.created_date) as year " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and v.id=:vendorId " +
            "     GROUP BY year ",nativeQuery=true)
    public List<Object[]> findOrderYearByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id), EXTRACT(YEAR_MONTH FROM o.created_date) as month " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and v.id=:vendorId " +
            "     GROUP BY month ",nativeQuery=true)
    public List<Object[]> findOrderYearMonthByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id),a.state " +
            "     FROM orders o, addresses a, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.shipping_address_id=a.id and o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and v.id=:vendorId " +
            "     GROUP BY a.state ",nativeQuery=true)
    public List<Object[]> findOrderBillingStateByVendor(Integer vendorId);

    @Query(value="SELECT count(distinct o.id),a.state " +
            "     FROM orders o, addresses a, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.shipping_address_id=a.id and o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and v.id=:vendorId " +
            "     GROUP BY a.state",nativeQuery=true)
    public List<Object[]> findOrderShippingStateByVendor(Integer vendorId);
}
