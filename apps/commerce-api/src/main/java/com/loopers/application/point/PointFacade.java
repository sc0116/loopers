package com.loopers.application.point;

import com.loopers.domain.point.PointInfo;
import com.loopers.domain.point.PointService;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PointFacade {

	private final PointService pointService;

	public PointResult getPoint(final Long userId) {
		final PointInfo pointInfo = pointService.getPoint(userId);

		if (pointInfo == null) {
			throw new CoreException(ErrorType.NOT_FOUND, "회원의 포인트가 존재하지 않습니다.");
		}

		return PointResult.from(pointInfo);
	}
}
