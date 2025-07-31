package com.loopers.interfaces.api.order;

import com.loopers.interfaces.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Order V1 API", description = "Order V1 API 입니다.")
public interface OrderV1ApiSpec {

	@Operation(
		summary = "상품 주문"
	)
	ApiResponse<Object> order(
		@Schema(name = "X-USER-ID")
		Long userId,
		@Schema(name = "주문 요청")
		OrderDto.V1.OrderRequest request
	);

	@Operation(
		summary = "단일 주문 상세 조회"
	)
	ApiResponse<OrderDto.V1.GetOrderDetailResponse> getOrder(
		@Schema(name = "X-USER-ID")
		Long userId,
		@Schema(name = "주문 ID")
		Long orderId
	);
}
