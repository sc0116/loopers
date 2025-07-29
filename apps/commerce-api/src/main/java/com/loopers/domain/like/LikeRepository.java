package com.loopers.domain.like;

import java.util.Optional;

public interface LikeRepository {

	Like save(Like like);

	boolean exists(Long userId, LikeTarget target);

	Optional<Like> find(Long userId, LikeTarget target);

	void delete(Like like);
}
