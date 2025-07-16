package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointResult;

public class PointV1Dto {

	public record PointResponse(
		Long id,
		Long amount
	) {

		public static PointResponse from(final PointResult pointResult) {
			return new PointResponse(
				pointResult.id(),
				pointResult.amount()
			);
		}
	}
}
