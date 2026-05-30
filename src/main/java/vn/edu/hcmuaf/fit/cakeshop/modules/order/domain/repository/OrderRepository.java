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
}
