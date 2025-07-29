package com.loopers.domain.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class LikeService {

	private final LikeRepository likeRepository;

	@Transactional
	public void like(final LikeCommand.Like command) {
		final Like like = new Like(command.userId(), command.target());

		if (likeRepository.exists(like.getUserId(), like.getTarget())) {
			return;
		}

		likeRepository.save(like);
	}

	@Transactional
	public void unlike(final LikeCommand.Unlike command) {
		likeRepository.find(command.userId(), command.target())
			.ifPresent(likeRepository::delete);
	}
}
