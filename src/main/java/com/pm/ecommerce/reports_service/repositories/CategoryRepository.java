package com.pm.ecommerce.reports_service.repositories;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value="select c.* " +
            " from orders o, orders_items oi, order_items i, products p, vendors v, categories c " +
            " where o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id " +
            "     and (:fromDate is null or o.created_date>:fromDate) " +
            "     and (:toDate is null or o.created_date<:toDate) " +
            "     and (:vendorId is null or v.id=:vendorId) " +
            " group by c.id " +
            " having 1=1 " +
            " and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            " and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
    , nativeQuery=true)
    public List<Category> findCategoryByReportRequest(@Param("fromDate") Timestamp fromDate,
                                                @Param("toDate") Timestamp toDate,
                                                @Param("vendorId") String vendorId,
                                                @Param("minCost") String minCost,
                                                @Param("maxCost") String maxCost);

    @Query(value="SELECT count(distinct o.id), sum(i.quantity*p.price) " +
            "     FROM orders o, orders_items oi, order_items i, products p, vendors v, categories c " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY c.id " +
            "     HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithoutGroupBy(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate,
                                                                  @Param("vendorId") String vendorId,
                                                                  @Param("minCost") String minCost,
                                                                  @Param("maxCost") String maxCost);

    @Query(value="SELECT EXTRACT(YEAR FROM o.created_date), count(distinct c.id), sum(i.quantity*p.price) " +
            "     FROM orders  o, orders_items oi, order_items i, products p, vendors v, categories c   " +
            "     WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "     GROUP BY EXTRACT(YEAR FROM o.created_date)" +
            "     HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByYear(@Param("fromDate") Timestamp fromDate,
                                                                   @Param("toDate") Timestamp toDate,
                                                                   @Param("vendorId") String vendorId,
                                                                   @Param("minCost") String minCost,
                                                                   @Param("maxCost") String maxCost);

    @Query(value="SELECT EXTRACT(YEAR_MONTH FROM o.created_date), count(distinct c.id), sum(i.quantity*p.price) " +
            "    FROM orders  o, orders_items oi, order_items i, products p, vendors v, categories c  " +
            "    WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY EXTRACT(YEAR_MONTH FROM o.created_date)" +
            "    HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByYearMonth(@Param("fromDate") Timestamp fromDate,
                                                                        @Param("toDate") Timestamp toDate,
                                                                        @Param("vendorId") String vendorId,
                                                                        @Param("minCost") String minCost,
                                                                        @Param("maxCost") String maxCost);

    @Query(value="SELECT DATE_FORMAT(o.created_date, '%Y %b %e') as week, count(distinct c.id), sum(i.quantity*p.price) " +
            "    FROM orders  o, orders_items oi, order_items i, products p, vendors v, categories c  " +
            "    WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY week " +
            "    HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByWeek(@Param("fromDate") Timestamp fromDate,
                                                                    @Param("toDate") Timestamp toDate,
                                                                    @Param("vendorId") String vendorId,
                                                                    @Param("minCost") String minCost,
                                                                    @Param("maxCost") String maxCost);

    @Query(value="SELECT EXTRACT(DAY FROM o.created_date), count(distinct c.id), sum(i.quantity*p.price) " +
            "    FROM orders  o, orders_items oi, order_items i, products p, vendors v, categories c  " +
            "    WHERE o.id=oi.order_id and oi.items_id=i.id and i.product_id=p.id and p.vendor_id=v.id and p.category_id=c.id  " +
            "           and (:fromDate is null or o.created_date>:fromDate) " +
            "           and (:toDate is null or o.created_date<:toDate) " +
            "           and (:vendorId is null or v.id=:vendorId) " +
            "    GROUP BY EXTRACT(DAY FROM o.created_date)" +
            "    HAVING 1=1 " +
            "           and (:minCost is null or sum(i.quantity*p.price)>:minCost) " +
            "           and (:maxCost is null or sum(i.quantity*p.price)<:maxCost) "
            ,nativeQuery=true)
    public List<Object[]> findCategoryByReportRequestWithGroupByDay(@Param("fromDate") Timestamp fromDate,
                                                                  @Param("toDate") Timestamp toDate,
                                                                  @Param("vendorId") String vendorId,
                                                                  @Param("minCost") String minCost,
                                                                  @Param("maxCost") String maxCost);

}
