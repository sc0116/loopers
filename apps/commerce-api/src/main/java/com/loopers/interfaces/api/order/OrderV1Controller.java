package com.loopers.interfaces.api.order;

import com.loopers.application.order.OrderCriteria;
import com.loopers.application.order.OrderFacade;
import com.loopers.application.order.OrderResult;
import com.loopers.domain.order.OrderCommand;
import com.loopers.domain.order.OrderInfo;
import com.loopers.domain.order.OrderService;
import com.loopers.interfaces.api.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderV1Controller implements OrderV1ApiSpec {

	private final OrderFacade orderFacade;
	private final OrderService orderService;

	@PostMapping
	public ApiResponse<Object> order(
		@RequestHeader("X-USER-ID") final Long userId,
		@RequestBody final OrderDto.V1.OrderRequest request
	) {
		orderFacade.order(request.toCriteria(userId));

		return ApiResponse.success();
	}

	@GetMapping
	public ApiResponse<OrderDto.V1.GetOrdersResponse> getOrders(
		@RequestHeader("X-USER-ID") final Long userId
	) {
		final List<OrderInfo> orderInfos = orderService.getOrders(new OrderCommand.GetOrders(userId));

		return ApiResponse.success(OrderDto.V1.GetOrdersResponse.from(orderInfos));
	}

	@GetMapping("/{orderId}")
	public ApiResponse<OrderDto.V1.GetOrderDetailResponse> getOrder(
		@RequestHeader("X-USER-ID") final Long userId,
		@PathVariable final Long orderId
	) {
		final OrderResult orderResult = orderFacade.getOrder(new OrderCriteria.GetOrder(userId, orderId));

		return ApiResponse.success(OrderDto.V1.GetOrderDetailResponse.from(orderResult));
	}
}
