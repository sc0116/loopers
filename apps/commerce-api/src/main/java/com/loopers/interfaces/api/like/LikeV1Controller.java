package com.loopers.interfaces.api.like;

import com.loopers.domain.like.LikeCommand;
import com.loopers.domain.like.LikeService;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@DeleteMapping("/products/{productId}")
	@Override
	public ApiResponse<Object> unlike(
		@RequestHeader("X-USER-ID") final Long userId,
		@PathVariable final Long productId
	) {
		likeService.unlike(LikeCommand.Unlike.unlikeProduct(userId, productId));

		return ApiResponse.success();
	}
}
