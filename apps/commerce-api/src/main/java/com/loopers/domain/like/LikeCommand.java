package com.loopers.domain.like;

public record LikeCommand() {

	public record Like(Long userId, LikeTarget target) {}

	public record Unlike(Long userId, LikeTarget target) {}
}
