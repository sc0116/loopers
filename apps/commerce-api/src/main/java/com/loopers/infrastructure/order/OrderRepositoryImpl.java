package com.loopers.infrastructure.order;

import com.loopers.domain.order.Order;
import com.loopers.domain.order.OrderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderRepositoryImpl implements OrderRepository {

	private final OrderJpaRepository orderJpaRepository;

	@Override
	public Order save(final Order order) {
		return orderJpaRepository.save(order);
	}

	@Override
	public Optional<Order> findBy(final Long id, final Long userId) {
		return orderJpaRepository.findByIdAndUserId(id, userId);
	}
}
