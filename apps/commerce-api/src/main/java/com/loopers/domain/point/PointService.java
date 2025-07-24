package com.loopers.domain.point;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class PointService {

	private final PointRepository pointRepository;

	@Transactional
	public PointInfo create(final PointCommand.Create command) {
		final Point point = new Point(command.userId(), new Amount(command.amount()));

		if (pointRepository.existsByUserId(point.getUserId())) {
			throw new CoreException(ErrorType.CONFLICT, "회원의 포인트가 이미 존재합니다.");
		}

		return PointInfo.from(pointRepository.save(point));
	}

	@Transactional(readOnly = true)
	public PointInfo getPoint(final Long userId) {
		return pointRepository.findByUserId(userId)
			.map(PointInfo::from)
			.orElse(null);
	}

	@Transactional
	public PointInfo charge(final PointCommand.Charge command) {
		final Point point = pointRepository.findByUserId(command.userId())
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 회원입니다."));

		point.charge(command.amount());

		return PointInfo.from(point);
	}
}
