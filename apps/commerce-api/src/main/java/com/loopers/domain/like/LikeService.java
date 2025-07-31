package com.loopers.domain.like;

import java.util.List;
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

	@Transactional(readOnly = true)
	public List<LikeInfo> getMyLikes(final LikeCommand.GetMyLikes command) {
		return likeRepository.findAll(command.userId(), command.type()).stream()
			.map(LikeInfo::from)
			.toList();
	}
}
