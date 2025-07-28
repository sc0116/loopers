package com.loopers.interfaces.api.product;

import com.loopers.interfaces.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product V1 API", description = "Product V1 API 입니다.")
public interface ProductV1ApiSpec {

	@Operation(
		summary = "상품 정보 조회"
	)
	ApiResponse<ProductDto.V1.GetResponse> getProduct(
		@Schema(name = "상품 ID")
		Long productId
	);
}
