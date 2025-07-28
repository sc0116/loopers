package com.loopers.domain.product;

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
class ProductServiceTest {

	@InjectMocks
	private ProductService sut;

	@Mock
	private ProductRepository productRepository;

	@DisplayName("상품 정보를 조회할 때, ")
	@Nested
	class Get {

		@DisplayName("존재하지 않는 상품 ID가 주어지면, NOT_FOUND 예외가 발생한다.")
		@Test
		void throwsNotFoundException_whenProductIdDoesNotExist() {
			given(productRepository.findById(anyLong()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.get(new ProductCommand.Get(-1L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 상품입니다."));
		}
	}
}
