package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Order;
import com.pm.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value="select p.* " +
            " from orders o, orders_items oi, order_items i, products p, vendors v " +
            " where o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "     and (:fromDate is null or o.created_date>:fromDate) " +
            "     and (:toDate is null or o.created_date<:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) " +
            " group by o.id " +
            " having 1=1 " +
            " and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            " and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
    , nativeQuery=true)
    public List<Product> findProductByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                @Param("toDate") Timestamp toDate,
                                                @Param("vendorId") String vendorId,
                                                @Param("minCost") String minCost,
                                                @Param("maxCost") String maxCost);

    @Query(value="SELECT count(distinct p.id) " +
            "     FROM orders o, orders_items oi, order_items i, products p, vendors v " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY oi.order_id " +
            "     HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findProductByReportRequestWithoutGroupBy(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate,
                                                                  @Param("vendorId") String vendorId,
                                                                  @Param("minCost") String minCost,
                                                                  @Param("maxCost") String maxCost);

    @Query(value="SELECT EXTRACT(YEAR FROM o.created_date), count(distinct p.id) " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v  " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY EXTRACT(YEAR FROM o.created_date)" +
            "     HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findProductByReportRequestWithGroupByYear(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate,
                                                                   @Param("vendorId") String vendorId,
                                                                   @Param("minCost") String minCost,
                                                                   @Param("maxCost") String maxCost);

    @Query(value="SELECT EXTRACT(YEAR_MONTH FROM o.created_date), count(distinct p.id) " +
            "    FROM orders  o, orders_items oi, order_items i, products p, vendors v " +
            "    WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY EXTRACT(YEAR_MONTH FROM o.created_date)" +
            "    HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findProductByReportRequestWithGroupByYearMonth(@Param("fromDate") Timestamp fromDate,
                                                                        @Param("toDate") Timestamp toDate,
                                                                        @Param("vendorId") String vendorId,
                                                                        @Param("minCost") String minCost,
                                                                        @Param("maxCost") String maxCost);

    @Query(value="SELECT EXTRACT(DAY FROM o.created_date), count(distinct p.id) " +
            "    FROM orders  o, orders_items oi, order_items i, products p, vendors v " +
            "    WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY EXTRACT(DAY FROM o.created_date)" +
            "    HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findProductByReportRequestWithGroupByDay(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate,
                                                                  @Param("vendorId") String vendorId,
                                                                  @Param("minCost") String minCost,
                                                                  @Param("maxCost") String maxCost);

}
