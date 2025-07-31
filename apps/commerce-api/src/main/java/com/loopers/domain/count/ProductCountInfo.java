package com.loopers.domain.count;

import static com.loopers.domain.count.ProductCount.Count;

public record ProductCountInfo(Long productId, Count likeCount) {

	public static ProductCountInfo from(final ProductCount productCount) {
		return new ProductCountInfo(productCount.getProductId(), productCount.getLikeCount());
	}

	public Long fetchLikeCount() {
		return likeCount.getCount();
	}
}
