package com.loopers.application.like;

import com.loopers.domain.like.LikeCommand;
import com.loopers.domain.like.LikeTarget;
import com.loopers.domain.product.ProductCommand;

import static com.loopers.domain.like.LikeTarget.TargetType;

public record LikeCriteria() {

	public record Like(
		Long userId,
		Long productId,
		TargetType type
	) {

		public static LikeCriteria.Like likeProduct(final Long userId, final Long productId) {
			return new LikeCriteria.Like(userId, productId, TargetType.PRODUCT);
		}

		public LikeCommand.Like toLikeCommand() {
			return new LikeCommand.Like(userId, new LikeTarget(type, productId));
		}

		public ProductCommand.GetProduct toProductCommand() {
			return new ProductCommand.GetProduct(productId);
		}
	}

	public record Unlike(
		Long userId,
		Long productId,
		TargetType type
	) {

		public static LikeCriteria.Unlike unlikeProduct(final Long userId, final Long productId) {
			return new LikeCriteria.Unlike(userId, productId, TargetType.PRODUCT);
		}

		public LikeCommand.Unlike toLikeCommand() {
			return new LikeCommand.Unlike(userId, new LikeTarget(type, productId));
		}

		public ProductCommand.GetProduct toProductCommand() {
			return new ProductCommand.GetProduct(productId);
		}
	}
}
