package com.loopers.domain.point;

public record PointCommand() {

	public record Create(Long userId, Long amount) { }

	public record Charge(Long userId, Long amount) { }

	public record Use(Long userId, Long amount) { }
}
