package com.loopers.domain.like;

import static com.loopers.domain.like.LikeTarget.TargetType;

public record LikeCommand() {

	public record Like(
		Long userId,
		LikeTarget target
	) {

		public static Like likeProduct(final Long userId, final Long productId) {
			return new Like(userId, new LikeTarget(TargetType.PRODUCT, productId));
		}
	}
}
