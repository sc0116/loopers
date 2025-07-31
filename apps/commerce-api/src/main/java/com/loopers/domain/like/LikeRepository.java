package com.loopers.domain.like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {

	Like save(Like like);

	boolean exists(Long userId, LikeTarget target);

	List<Like> findAll(Long userId, LikeTarget.TargetType type);

	Optional<Like> find(Long userId, LikeTarget target);

	void delete(Like like);
}
