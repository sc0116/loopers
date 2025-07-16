package com.loopers.domain.point;

public record PointInfo(
	Long id,
	Long userId,
	Long amount
) {

	public static PointInfo from(final Point point) {
		return new PointInfo(
			point.getId(),
			point.getUserId(),
			point.getAmount()
		);
	}
}
