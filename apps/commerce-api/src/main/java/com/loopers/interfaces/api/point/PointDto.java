package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointCriteria;
import com.loopers.application.point.PointResult;

public record PointDto() {
	
	public record V1() {

		public record ChargeRequest(
			Long amount
		) {

			public PointCriteria.Charge toCriteria(final Long userId) {
				return new PointCriteria.Charge(userId, amount);
			}
		}

		public record GetResponse(
			Long id,
			Long amount
		) {

			public static GetResponse from(final PointResult pointResult) {
				return new GetResponse(
					pointResult.id(),
					pointResult.amount()
				);
			}
		}
	}
}
