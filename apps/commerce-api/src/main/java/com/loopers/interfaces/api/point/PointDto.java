package com.loopers.interfaces.api.point;

import com.loopers.domain.point.PointCommand;
import com.loopers.domain.point.PointInfo;

public record PointDto() {
	
	public record V1() {

		public record GetPointResponse(Long id, Long amount) {

			public static GetPointResponse from(final PointInfo pointInfo) {
				return new GetPointResponse(pointInfo.id(), pointInfo.fetchAmount());
			}
		}

		public record ChargeRequest(Long amount) {

			public PointCommand.Charge toCommand(final Long userId) {
				return new PointCommand.Charge(userId, amount);
			}
		}
	}
}
