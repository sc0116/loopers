package com.loopers.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class BrandIdTest {

	@DisplayName("상품 브랜드 ID를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("브랜드 ID로 NULL 값이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenBrandIdIsNull(final Long brandId) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new BrandId(brandId);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "브랜드 ID는 비어있을 수 없습니다."));
		}
	}
}
