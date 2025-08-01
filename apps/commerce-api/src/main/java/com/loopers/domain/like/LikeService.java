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

		if (likeRepository.existsBy(like.getUserId(), like.getTarget())) {
			return;
		}

		likeRepository.save(like);
	}

	@Transactional(readOnly = true)
	public List<LikeInfo> getMyLikes(final LikeCommand.GetMyLikes command) {
		return likeRepository.findAllBy(command.userId(), command.type()).stream()
			.map(LikeInfo::from)
			.toList();
	}

	@Transactional
	public void unlike(final LikeCommand.Unlike command) {
		likeRepository.findBy(command.userId(), command.target())
			.ifPresent(likeRepository::delete);
	}
}
