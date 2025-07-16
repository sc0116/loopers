package com.loopers.domain.point;

public interface PointRepository {

	Point save(Point point);

	boolean existsByUserId(Long userId);
}
