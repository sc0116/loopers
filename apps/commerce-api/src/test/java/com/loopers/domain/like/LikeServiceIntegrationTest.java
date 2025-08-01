package com.loopers.domain.like;

import static com.loopers.domain.like.LikeTarget.TargetType;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class LikeServiceIntegrationTest {

	@Autowired
	private LikeService sut;

	@Autowired
	private LikeRepository likeRepository;

	@DisplayName("좋아요를 등록할 때, ")
	@Nested
	class PostLike {

		@DisplayName("회원이 대상에 대해 좋아요하지 않은 상태라면, 좋아요를 등록한다.")
		@Test
		void postLike_whenUserNotYetLikedTarget() {
			final LikeTarget target = new LikeTarget(TargetType.PRODUCT, 10L);

			sut.like(new LikeCommand.Like(1L, target));

			assertThat(likeRepository.existsBy(1L, target)).isTrue();
		}
	}

	@DisplayName("좋아요를 취소할 때, ")
	@Nested
	class Unlike {

		@DisplayName("회원이 대상에 대해 이미 좋아요한 상태라면, 좋아요를 취소한다.")
		@Test
		void postLike_whenUserAlreadyLikedTarget() {
			final Like like = likeProduct(1L, 10L);

			sut.unlike(new LikeCommand.Unlike(like.getUserId(), like.getTarget()));

			assertThat(likeRepository.existsBy(like.getUserId(), like.getTarget())).isFalse();
		}
	}

	private Like likeProduct(final Long userId, final Long productId) {
		final Like like = new Like(userId, new LikeTarget(TargetType.PRODUCT, productId));

		return likeRepository.save(like);
	}
}
