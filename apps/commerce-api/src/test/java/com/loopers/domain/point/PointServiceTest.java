package com.loopers.domain.point;

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
class PointServiceTest {

	@InjectMocks
	private PointService sut;

	@Mock
	private PointRepository pointRepository;

	@DisplayName("포인트를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("해당 회원의 포인트가 이미 존재하면, CONFLICT 예외가 발생한다.")
		@Test
		void throwsConflictException_whenUserIdAlreadyExists() {
			given(pointRepository.existsByUserId(anyLong()))
				.willReturn(true);

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.create(new PointCommand.Create(1L, 2L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.CONFLICT, "회원의 포인트가 이미 존재합니다."));
		}
	}

	@DisplayName("포인트 정보 조회할 때, ")
	@Nested
	class Read {

		@DisplayName("존재하지 않는 회원 ID가 주어지면, NOT_FOUND 예외를 반환한다.")
		@Test
		void returnThrows_whenPointNonExists() {
			given(pointRepository.findByUserId(anyLong()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.get(-1L);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "회원의 포인트가 존재하지 않습니다."));
		}
	}

	@DisplayName("포인트 충전할 때, ")
	@Nested
	class Charge {

		@DisplayName("존재하지 않는 회원 ID가 주어지면, NOT_FOUND 예외가 반환된다.")
		@Test
		void throwsNotFoundException_whenUserDoesNotExist() {
			given(pointRepository.findByUserId(anyLong()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.charge(new PointCommand.Charge(-1L, 1L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 회원입니다."));
		}
	}
}
