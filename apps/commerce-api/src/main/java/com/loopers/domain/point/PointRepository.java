package com.loopers.domain.point;

import java.util.Optional;

public interface PointRepository {

	Point save(Point point);

	boolean existsByUserId(Long userId);

	Optional<Point> findByUserId(Long userId);
}
