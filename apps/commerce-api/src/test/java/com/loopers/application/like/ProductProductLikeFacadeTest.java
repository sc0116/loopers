package com.loopers.application.like;

import com.loopers.domain.like.LikeService;
import com.loopers.domain.product.ProductService;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductProductLikeFacadeTest {

	@InjectMocks
	private ProductLikeFacade sut;

	@Mock
	private LikeService likeService;

	@Mock
	private ProductService productService;

	@DisplayName("상품 좋아요를 등록할 때, ")
	@Nested
	class PostLike {

		@DisplayName("존재하지 않는 상품을 좋아요 등록하면, NOT_FOUND 예외가 발생한다.")
		@Test
		void throwsNotFoundException_whenProductDoesNotExist() {
			given(productService.findProduct(any()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.like(LikeCriteria.Like.likeProduct(1L, 10L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "좋아요 등록할 상품이 존재하지 않습니다."));
		}
	}

	@DisplayName("상품 좋아요를 취소할 때, ")
	@Nested
	class Unlike {

		@DisplayName("존재하지 않는 상품을 좋아요 취소하면, NOT_FOUND 예외가 발생한다.")
		@Test
		void throwsNotFoundException_whenProductDoesNotExist() {
			given(productService.findProduct(any()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.unlike(LikeCriteria.Unlike.unlikeProduct(1L, 10L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "좋아요 취소할 상품이 존재하지 않습니다."));
		}
	}
}
