package com.loopers.domain.like;

import com.loopers.domain.like.LikeTarget.TargetType;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LikeTest {

	@DisplayName("좋아요를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("회원 ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenUserIdIsNull(final Long userId) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Like(userId, new LikeTarget(TargetType.PRODUCT, 1L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "회원 ID는 비어있을 수 없습니다."));
		}
	}
}
