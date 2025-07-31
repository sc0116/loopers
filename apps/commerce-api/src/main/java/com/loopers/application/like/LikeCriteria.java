package com.loopers.application.like;

import static com.loopers.domain.count.ProductCount.CountType;
import static com.loopers.domain.like.LikeTarget.TargetType;

import com.loopers.domain.count.ProductCountCommand;
import com.loopers.domain.like.LikeCommand;
import com.loopers.domain.like.LikeTarget;
import com.loopers.domain.product.ProductCommand;

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

		public ProductCountCommand.Increment toCountCommand() {
			return new ProductCountCommand.Increment(productId, CountType.LIKE);
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

		public ProductCountCommand.Decrement toCountCommand() {
			return new ProductCountCommand.Decrement(productId, CountType.LIKE);
		}
	}
}
