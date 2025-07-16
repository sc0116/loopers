package com.loopers.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

		@DisplayName("해당 회원의 포인트가 이미 존재하면, CONFLICT 예외가 발생한다.")
		@Test
		void throwsConflictException_whenUserIdAlreadyExists() {
			final Long userId = 1L;
			createPoint(userId, 1L);

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.create(new PointCommand.Create(userId, 2L));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.CONFLICT, "회원의 포인트가 이미 존재합니다."));
		}

		@DisplayName("새로운 회원이 주어지면, 정상적으로 생성한다.")
		@Test
		void save() {
			final PointCommand.Create command = new PointCommand.Create(1L, 1L);

			sut.create(command);

			verify(pointRepository, times(1)).save(any());
		}
	}

	private Point createPoint(final Long userId, final Long amount) {
		final Point point = new Point(userId, amount);

		return pointRepository.save(point);
	}
}
