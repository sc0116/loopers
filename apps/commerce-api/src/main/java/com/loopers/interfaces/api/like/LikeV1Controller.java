package com.loopers.interfaces.api.like;

import com.loopers.application.like.LikeCriteria;
import com.loopers.application.like.LikeResult;
import com.loopers.application.like.ProductLikeFacade;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/like")
public class LikeV1Controller implements LikeV1ApiSpec {

	private final ProductLikeFacade productLikeFacade;

	@GetMapping("/products")
	@Override
	public ApiResponse<LikeDto.V1.GetMyProductsResponse> getMyProducts(
		@RequestHeader("X-USER-ID") final Long userId
	) {
		final LikeResult.GetMyProducts result = productLikeFacade.getMyProducts(new LikeCriteria.GetMyProducts(userId));

		return ApiResponse.success(LikeDto.V1.GetMyProductsResponse.from(result));
	}

	@PostMapping("/products/{productId}")
	@Override
	public ApiResponse<Object> like(
		@RequestHeader("X-USER-ID") final Long userId,
		@PathVariable final Long productId
	) {
		productLikeFacade.like(LikeCriteria.Like.likeProduct(userId, productId));

		return ApiResponse.success();
	}

	@DeleteMapping("/products/{productId}")
	@Override
	public ApiResponse<Object> unlike(
		@RequestHeader("X-USER-ID") final Long userId,
		@PathVariable final Long productId
	) {
		productLikeFacade.unlike(LikeCriteria.Unlike.unlikeProduct(userId, productId));

		return ApiResponse.success();
	}
}
