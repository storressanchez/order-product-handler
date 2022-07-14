package com.rohlik.interview.application.repository;

import com.rohlik.interview.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findById(long id);

    List<Order> findAll();

    @Query(value = "SELECT * FROM orders WHERE status = :status AND created_at < :time", nativeQuery = true)
    List<Order> findWithStatusOlderThan(@Param("status") String status, @Param("time") Instant time);
}
