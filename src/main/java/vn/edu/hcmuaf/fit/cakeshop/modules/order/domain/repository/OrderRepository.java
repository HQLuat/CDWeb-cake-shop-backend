package vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.Order;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.cakeshop.modules.order.domain.entity.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items i LEFT JOIN FETCH i.product WHERE o.id = :orderId")
    Optional<Order> findByIdWithItems(@Param("orderId") Long orderId);

    @Query(value = """
    SELECT DISTINCT o FROM Order o
    LEFT JOIN FETCH o.items i
    LEFT JOIN FETCH i.product
    WHERE o.user.id = :userId
    AND (:orderStatus IS NULL OR o.orderStatus = :orderStatus)
    AND (:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus)
    AND (:keyword IS NULL OR :keyword = ''
        OR LOWER(o.shippingAddress) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """,
            countQuery = """
    SELECT COUNT(DISTINCT o) FROM Order o
    WHERE o.user.id = :userId
    AND (:orderStatus IS NULL OR o.orderStatus = :orderStatus)
    AND (:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus)
    AND (:keyword IS NULL OR :keyword = ''
        OR LOWER(o.shippingAddress) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    Page<Order> findPageByUserId(
            @Param("userId") Long userId,
            @Param("keyword") String keyword,
            @Param("orderStatus") OrderStatus orderStatus,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            Pageable pageable);

    @Query(value = """
    SELECT DISTINCT o FROM Order o
    LEFT JOIN FETCH o.items i
    LEFT JOIN FETCH i.product
    LEFT JOIN o.user u
    WHERE (:orderStatus IS NULL OR o.orderStatus = :orderStatus)
    AND (:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus)
    AND (:keyword IS NULL OR :keyword = ''
        OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(o.shippingAddress) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """,
            countQuery = """
    SELECT COUNT(DISTINCT o) FROM Order o
    LEFT JOIN o.user u
    WHERE (:orderStatus IS NULL OR o.orderStatus = :orderStatus)
    AND (:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus)
    AND (:keyword IS NULL OR :keyword = ''
        OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(o.shippingAddress) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    Page<Order> findAllWithFilters(
            @Param("keyword") String keyword,
            @Param("orderStatus") OrderStatus orderStatus,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            Pageable pageable);

    // ─── ANALYTICS QUERIES ────────────────────────────────────────────────────

    long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    long countByOrderStatusAndCreatedAtBetween(OrderStatus status, LocalDateTime from, LocalDateTime to);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o " +
           "WHERE o.paymentStatus = 'PAID' AND o.createdAt BETWEEN :from AND :to")
    BigDecimal sumRevenueByPaidStatus(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("SELECT FUNCTION('DATE', o.createdAt), SUM(o.totalAmount) FROM Order o " +
           "WHERE o.paymentStatus = 'PAID' AND o.createdAt BETWEEN :from AND :to " +
           "GROUP BY FUNCTION('DATE', o.createdAt) ORDER BY FUNCTION('DATE', o.createdAt)")
    List<Object[]> revenueGroupByDate(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("SELECT o.orderStatus, COUNT(o) FROM Order o " +
           "WHERE o.createdAt BETWEEN :from AND :to GROUP BY o.orderStatus")
    List<Object[]> countByStatusInRange(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("SELECT i.product.name, SUM(i.quantity), SUM(i.subtotal) FROM OrderItem i " +
           "WHERE i.order.createdAt BETWEEN :from AND :to " +
           "GROUP BY i.product.id, i.product.name ORDER BY SUM(i.quantity) DESC")
    List<Object[]> topProductsByQuantity(@Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to,
                                         Pageable pageable);

    List<Order> findTop10ByOrderByCreatedAtDesc();
}
