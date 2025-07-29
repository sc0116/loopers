package com.loopers.domain.like;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.loopers.domain.like.LikeTarget.TargetType;
import static org.assertj.core.api.Assertions.assertThat;

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
			sut.like(new LikeCommand.Like(1L, new LikeTarget(TargetType.PRODUCT, 10L)));

			final boolean actual = likeRepository.exists(1L, new LikeTarget(TargetType.PRODUCT, 10L));

			assertThat(actual).isTrue();
		}
	}
}
