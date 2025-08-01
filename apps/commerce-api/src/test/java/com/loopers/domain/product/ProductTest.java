package com.loopers.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

	@DisplayName("상품을 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("상품명이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {" "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenNameIsNullOrBlank(final String name) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Product(new BrandId(1L), name, "짱구는 못말립니다.", BigDecimal.TEN);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품명은 비어있을 수 없습니다."));
		}

		@DisplayName("상품 설명이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {" "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenDescriptionIsNullOrBlank(final String description) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Product(new BrandId(1L), "짱구", description, BigDecimal.TEN);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품 설명은 비어있을 수 없습니다."));
		}

		@DisplayName("상품 가격이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenPriceIsNull(final BigDecimal price) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Product(new BrandId(1L), "짱구", "짱구는 못말립니다.", price);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품 가격은 비어있을 수 없습니다."));
		}

		@DisplayName("상품 가격이 0원 미만이면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(longs = {-2L, -1L})
		@ParameterizedTest
		void throwsBadRequestException_whenPriceIsNegative(final long price) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Product(new BrandId(1L), "짱구", "짱구는 못말립니다.", BigDecimal.valueOf(price));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품 가격은 음수일 수 없습니다."));
		}
	}
}
