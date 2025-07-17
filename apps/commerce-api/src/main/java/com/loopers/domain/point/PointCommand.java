package com.loopers.domain.point;

public class PointCommand {

	public record Create(
		Long userId,
		Long amount
	) {

		public Point toPoint() {
			return new Point(userId, new Amount(amount));
		}
	}
}
