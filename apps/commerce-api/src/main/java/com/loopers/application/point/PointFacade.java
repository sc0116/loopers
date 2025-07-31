package com.loopers.application.point;

import com.loopers.domain.point.PointCommand;
import com.loopers.domain.point.PointInfo;
import com.loopers.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PointFacade {

	private final PointService pointService;

	public PointResult getPoint(final Long userId) {
		final PointInfo pointInfo = pointService.getPoint(new PointCommand.GetPoint(userId));

		return PointResult.from(pointInfo);
	}

	public PointResult charge(final PointCriteria.Charge criteria) {
		final PointInfo pointInfo = pointService.charge(criteria.toCommand());

		return PointResult.from(pointInfo);
	}
}
