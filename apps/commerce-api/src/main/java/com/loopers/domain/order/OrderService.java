package com.loopers.domain.order;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
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

	@Transactional(readOnly = true)
	public OrderInfo getOrder(final OrderCommand.GetOrder command) {
		return orderRepository.findBy(command.orderId(), command.userId())
			.map(OrderInfo::from)
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 주문입니다."));
	}

	@Transactional(readOnly = true)
	public List<OrderInfo> getOrders(final OrderCommand.GetOrders command) {
		return orderRepository.findAllBy(command.userId()).stream()
			.map(OrderInfo::from)
			.toList();
	}
}
