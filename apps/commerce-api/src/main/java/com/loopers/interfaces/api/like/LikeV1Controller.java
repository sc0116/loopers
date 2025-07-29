package com.loopers.interfaces.api.like;

import com.loopers.domain.like.LikeCommand;
import com.loopers.domain.like.LikeService;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/like")
public class LikeV1Controller implements LikeV1ApiSpec {

	private final LikeService likeService;

	@PostMapping("/products/{productId}")
	@Override
	public ApiResponse<Object> like(
		@RequestHeader("X-USER-ID") final Long userId,
		@PathVariable final Long productId
	) {
		likeService.like(LikeCommand.Like.likeProduct(userId, productId));

		return ApiResponse.success();
	}
}
