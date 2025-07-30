package com.loopers.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductStockServiceTest {

	@InjectMocks
	private ProductStockService sut;

	@Mock
	private ProductStockRepository productStockRepository;

	@DisplayName("상품 재고를 차감할 때, ")
	@Nested
	class Decrement {

		@DisplayName("존재하지 않는 상품 ID가 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenProductIdDoesNotExist() {
			given(productStockRepository.findByProductId(anyLong()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.decrement(new ProductStockCommand.Decrement(-1L, 1));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "상품 재고가 존재하지 않습니다."));
		}
	}
}
