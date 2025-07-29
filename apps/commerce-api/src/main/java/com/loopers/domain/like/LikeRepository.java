package com.loopers.domain.like;

public interface LikeRepository {

	Like save(Like like);

	boolean exists(Long userId, LikeTarget target);
}
