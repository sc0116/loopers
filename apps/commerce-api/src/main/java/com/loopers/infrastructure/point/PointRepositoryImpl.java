package com.loopers.infrastructure.point;

import com.loopers.domain.point.Point;
import com.loopers.domain.point.PointRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PointRepositoryImpl implements PointRepository {

	private final PointJpaRepository pointJpaRepository;

	@Override
	public Point save(final Point point) {
		return pointJpaRepository.save(point);
	}

	@Override
	public boolean existsByUserId(final Long userId) {
		return pointJpaRepository.existsByUserId(userId);
	}

	@Override
	public Optional<Point> findByUserId(final Long userId) {
		return pointJpaRepository.findByUserId(userId);
	}
}
