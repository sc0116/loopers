package com.loopers.domain.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class OrderService {

	private final OrderRepository orderRepository;

	@Transactional
	public OrderInfo order(final OrderCommand.Order command) {
		final List<OrderItem> items = command.items().stream()
			.map(item -> new OrderItem(item.productId(), item.quantity(), item.price()))
			.toList();
		final Order order = new Order(command.userId(), items);

		return OrderInfo.from(orderRepository.save(order));
	}
}
