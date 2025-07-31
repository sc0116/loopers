package com.loopers.interfaces.api.like;

import com.loopers.interfaces.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Like V1 API", description = "Like V1 API 입니다.")
public interface LikeV1ApiSpec {

	@Operation(
		summary = "내가 좋아요 한 상품 목록 조회"
	)
	ApiResponse<LikeDto.V1.GetMyProductsResponse> getMyProducts(
		@Schema(name = "X-USER-ID")
		Long userId
	);

	@Operation(
		summary = "상품 좋아요 등록"
	)
	ApiResponse<Object> like(
		@Schema(name = "X-USER-ID")
		Long userId,
		@Schema(name = "상품 ID")
		Long productId
	);

	@Operation(
		summary = "상품 좋아요 취소"
	)
	ApiResponse<Object> unlike(
		@Schema(name = "X-USER-ID")
		Long userId,
		@Schema(name = "상품 ID")
		Long productId
	);
}
