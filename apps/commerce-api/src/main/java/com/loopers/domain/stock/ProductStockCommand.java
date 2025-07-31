package com.loopers.domain.stock;

public record ProductStockCommand() {

	public record GetStock(Long productId) {}

	public record Decrement(Long productId, Integer quantity) {}
}
