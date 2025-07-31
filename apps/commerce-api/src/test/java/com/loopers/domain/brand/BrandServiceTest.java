package com.loopers.domain.brand;

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
class BrandServiceTest {

	@InjectMocks
	private BrandService sut;

	@Mock
	private BrandRepository brandRepository;

	@DisplayName("브랜드 정보를 조회할 때, ")
	@Nested
	class Get {

		@DisplayName("존재하지 않는 브랜드 ID가 주어지면, NOT_FOUND 예외가 발생한다.")
		@Test
		void throwsNotFoundException_whenBrandIdDoesNotExist() {
			given(brandRepository.findBy(anyLong()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.getBrand(new BrandCommand.GetBrand(-1L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 브랜드입니다."));
		}
	}
}
