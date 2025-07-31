package com.loopers.domain.like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {

	Like save(Like like);

	boolean existsBy(Long userId, LikeTarget target);

	Optional<Like> findBy(Long userId, LikeTarget target);

	List<Like> findAllBy(Long userId, LikeTarget.TargetType type);

	void delete(Like like);
}
