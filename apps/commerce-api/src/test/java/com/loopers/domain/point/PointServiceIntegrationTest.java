package com.loopers.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SpringBootTest
class PointServiceIntegrationTest {

	@Autowired
	private PointService sut;

	@MockitoSpyBean
	private PointJpaRepository pointRepository;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("포인트를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("새로운 회원이 주어지면, 정상적으로 생성한다.")
		@Test
		void save() {
			final PointCommand.Create command = new PointCommand.Create(1L, 1L);

			sut.create(command);

			verify(pointRepository, times(1)).save(any());
		}
	}

	@DisplayName("포인트 정보 조회할 때, ")
	@Nested
	class Read {

		@DisplayName("존재하지 않는 회원 ID가 주어지면, NOT_FOUND 예외를 반환한다.")
		@Test
		void returnThrows_whenPointNonExists() {
			final Long invalidUserId = -1L;

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.getPoint(new PointCommand.GetPoint(invalidUserId));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "회원의 포인트가 존재하지 않습니다."));
		}

		@DisplayName("존재하는 회원 ID가 주어지면, 정보를 반환한다.")
		@Test
		void returnPointInfo_whenPointAlreadyExists() {
			final Point point = createPoint(1L, 1L);

			final PointInfo actual = sut.getPoint(new PointCommand.GetPoint(point.getUserId()));

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new PointInfo(
					point.getId(),
					1L,
					new Amount(1L)
				));
		}
	}

	@DisplayName("포인트 충전할 때, ")
	@Nested
	class Charge {

		@DisplayName("존재하지 않는 회원 ID가 주어지면, NOT_FOUND 예외가 반환된다.")
		@Test
		void throwsNotFoundException_whenUserDoesNotExist() {
			final Long invalidUserId = -1L;

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.charge(new PointCommand.Charge(invalidUserId, 1L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 회원입니다."));
		}
	}

	private Point createPoint(final Long userId, final Long amount) {
		final Point point = new Point(userId, new Amount(amount));

		return pointRepository.save(point);
	}
}
