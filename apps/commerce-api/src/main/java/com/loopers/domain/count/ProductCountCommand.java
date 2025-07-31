package com.loopers.domain.count;

import static com.loopers.domain.count.ProductCount.CountType;

import java.util.List;

public record ProductCountCommand() {

	public record Increment(Long productId, CountType countType) {}

	public record GetProductCount(Long productId) {}

	public record GetProductCounts(List<Long> productIds) {}

	public record Decrement(Long productId, CountType countType) {}
}
