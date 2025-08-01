package com.loopers.domain.order;

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
class OrderServiceTest {

	@InjectMocks
	private OrderService sut;

	@Mock
	private OrderRepository orderRepository;

	@DisplayName("주문 정보를 조회할 때, ")
	@Nested
	class GetOrder {

		@DisplayName("존재하지 않는 주문 ID가 주어지면, NOT_FOUND 예외가 발생한다.")
		@Test
		void throwsNotFoundException_whenOrderDoesNotExist() {
			given(orderRepository.findBy(anyLong(), anyLong()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.getOrder(new OrderCommand.GetOrder(1L, 10L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 주문입니다."));
		}
	}
}
