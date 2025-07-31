package com.loopers.domain.order;

import java.util.Optional;

public interface OrderRepository {

	Order save(Order order);

	Optional<Order> findBy(Long id, Long userId);
}
