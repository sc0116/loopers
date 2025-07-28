package com.loopers.domain.brand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class BrandTest {

	@DisplayName("브랜드를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("브랜드명이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {" "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenNameIsNullOrBlank(final String name) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Brand(name, "description");
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "브랜드명은 비어있을 수 없습니다."));
		}

		@DisplayName("브랜드 설명이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {" "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenDescriptionIsNullOrBlank(final String description) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Brand("name", description);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "브랜드 설명은 비어있을 수 없습니다."));
		}
	}
}
