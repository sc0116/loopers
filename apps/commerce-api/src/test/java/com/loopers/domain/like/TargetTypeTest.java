package com.loopers.domain.like;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.loopers.domain.like.LikeTarget.TargetType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TargetTypeTest {

	@DisplayName("유효하지 않은 값이 주어지면, BAD_REQUEST 예외가 발생한다.")
	@NullAndEmptySource
	@ParameterizedTest
	void throwsBadRequestException_whenTargetTypeFormatInvalid(final String targetType) {
		final CoreException actual = assertThrows(CoreException.class, () -> {
			TargetType.from(targetType);
		});

		assertThat(actual).usingRecursiveComparison()
			.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "좋아요 대상 타입이 유효하지 않습니다."));
	}
}
