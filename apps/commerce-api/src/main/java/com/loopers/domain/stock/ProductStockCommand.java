package com.loopers.domain.stock;

import java.util.List;

public record ProductStockCommand() {

	public record GetStocks(List<Long> productIds) {}

	public record Decrement(Long productId, Integer quantity) {}
}
