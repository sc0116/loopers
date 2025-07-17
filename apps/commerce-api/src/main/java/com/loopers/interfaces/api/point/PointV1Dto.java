package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointCriteria;
import com.loopers.application.point.PointResult;

public class PointV1Dto {

	public record PointChargeRequest(
		Long amount
	) {

		public PointCriteria.Charge toCriteria(final Long userId) {
			return new PointCriteria.Charge(userId, amount);
		}
	}

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
