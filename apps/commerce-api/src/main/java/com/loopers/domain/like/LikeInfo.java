package com.loopers.domain.like;

public record LikeInfo(Long userId, LikeTarget target) {

	public static LikeInfo from(final Like like) {
		return new LikeInfo(like.getUserId(), like.getTarget());
	}

	public Long fetchTargetId() {
		return target.getId();
	}
}
