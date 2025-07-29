package com.loopers.domain.like;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.loopers.domain.like.LikeTarget.TargetType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

	@InjectMocks
	private LikeService sut;

	@Mock
	private LikeRepository likeRepository;

	@DisplayName("좋아요를 등록할 때, ")
	@Nested
	class PostLike {

		@DisplayName("회원이 대상에 대해 이미 좋아요한 상태라면, 좋아요를 등록하지 않는다.")
		@Test
		void doesNothing_whenUserAlreadyLikedTarget() {
			given(likeRepository.exists(anyLong(), any()))
				.willReturn(true);

			sut.like(new LikeCommand.Like(1L, new LikeTarget(TargetType.PRODUCT, 1L)));

			verify(likeRepository, never())
				.save(new Like(1L, new LikeTarget(TargetType.PRODUCT, 1L)));
		}
	}
}
