package com.loopers.domain.like;

import static com.loopers.domain.like.LikeTarget.TargetType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
			given(likeRepository.existsBy(anyLong(), any()))
				.willReturn(true);

			sut.like(new LikeCommand.Like(1L, new LikeTarget(TargetType.PRODUCT, 1L)));

			verify(likeRepository, never())
				.save(new Like(1L, new LikeTarget(TargetType.PRODUCT, 1L)));
		}
	}

	@DisplayName("좋아요를 취소할 때, ")
	@Nested
	class Unlike {

		@DisplayName("회원이 대상에 대해 좋아요 하지 않은 상태라면, 좋아요를 취소하지 않는다.")
		@Test
		void doesNothing_whenUserNotYetLikedTarget() {
			given(likeRepository.findBy(anyLong(), any()))
				.willReturn(Optional.of(mock(Like.class)));

			sut.unlike(new LikeCommand.Unlike(1L, new LikeTarget(TargetType.PRODUCT, 1L)));

			verify(likeRepository, never())
				.delete(new Like(1L, new LikeTarget(TargetType.PRODUCT, 1L)));
		}
	}
}
