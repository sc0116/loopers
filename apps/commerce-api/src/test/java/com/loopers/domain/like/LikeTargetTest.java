package com.loopers.domain.like;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static com.loopers.domain.like.LikeTarget.TargetType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class LikeTargetTest {

	@DisplayName("좋아요 대상을 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("좋아요 대상 ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenTargetIdIsNull(final Long targetId) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new LikeTarget(TargetType.PRODUCT, targetId);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "좋아요 대상 ID는 비어있을 수 없습니다."));
		}
	}
}
