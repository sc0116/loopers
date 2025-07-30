package com.loopers.interfaces.api.like;

import com.loopers.application.like.LikeCriteria;
import com.loopers.application.like.ProductLikeFacade;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/like")
public class LikeV1Controller implements LikeV1ApiSpec {

	private final ProductLikeFacade productLikeFacade;

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
