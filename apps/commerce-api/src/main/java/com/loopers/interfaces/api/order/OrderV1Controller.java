package com.loopers.interfaces.api.order;

import com.loopers.application.order.OrderFacade;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
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

	@PostMapping
	public ApiResponse<Object> order(
		@RequestHeader("X-USER-ID") final Long userId,
		@RequestBody final OrderDto.V1.OrderRequest request
	) {
		orderFacade.order(request.toCriteria(userId));

		return ApiResponse.success();
	}
}
