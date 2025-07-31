package com.loopers.infrastructure.like;

import com.loopers.domain.like.Like;
import com.loopers.domain.like.LikeRepository;
import com.loopers.domain.like.LikeTarget;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LikeRepositoryImpl implements LikeRepository {

	private final LikeJpaRepository likeJpaRepository;

	@Override
	public Like save(final Like like) {
		return likeJpaRepository.save(like);
	}

	@Override
	public boolean exists(final Long userId, final LikeTarget target) {
		return likeJpaRepository.existsByUserIdAndTarget(userId, target);
	}

	@Override
	public List<Like> findAll(final Long userId, final LikeTarget.TargetType type) {
		return likeJpaRepository.findAllByUserIdAndTargetType(userId, type);
	}

	@Override
	public Optional<Like> find(final Long userId, final LikeTarget target) {
		return likeJpaRepository.findByUserIdAndTarget(userId, target);
	}

	@Override
	public void delete(final Like like) {
		likeJpaRepository.deleteById(like.getId());
	}
}
