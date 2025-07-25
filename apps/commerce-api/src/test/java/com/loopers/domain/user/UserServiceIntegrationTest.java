package com.loopers.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.loopers.infrastructure.user.UserJpaRepository;
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
class UserServiceIntegrationTest {

	@Autowired
	private UserService sut;

	@MockitoSpyBean
	private UserJpaRepository userRepository;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("유저를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("이미 존재하는 ID가 주어지면, CONFLICT 예외가 발생한다.")
		@Test
		void throwsConflictException_whenIdAlreadyExists() {
			final String loginId = "jjanggu";
			createUser(loginId, "jjanggu@gmail.com", "2025-01-01");

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.register(new UserCommand.Register(loginId, "jjanggu@naver.com", "2025-01-01", Gender.MALE.name()));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.CONFLICT, "이미 존재하는 ID입니다."));
		}

		@DisplayName("새로운 ID와 이메일이 주어지면, 정상적으로 생성한다.")
		@Test
		void save() {
			final UserCommand.Register command = new UserCommand.Register(
				"jjanggu",
				"jjanggu@gmail.com",
				"2025-01-01",
				Gender.MALE.name()
			);

			sut.register(command);

			verify(userRepository, times(1)).save(any());
		}
	}

	@DisplayName("유저 정보 조회할 때, ")
	@Nested
	class Read {

		@DisplayName("존재하지 않는 회원 ID가 주어지면, NOT_FOUND 예외를 반환한다.")
		@Test
		void returnThrows_whenUserNonExists() {
			final Long invalidUserId = -1L;

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.get(invalidUserId);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 회원입니다."));
		}

		@DisplayName("존재하는 회원 ID가 주어지면, 정보를 반환한다.")
		@Test
		void returnUserInfo_whenUserAlreadyExists() {
			final User user = createUser("jjanggu", "jjanggu@gmail.com", "2025-01-01");

			final UserInfo actual = sut.get(user.getId());

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new UserInfo(
					user.getId(),
					new LoginId("jjanggu"),
					new Email("jjanggu@gmail.com"),
					new BirthDate("2025-01-01"),
					Gender.MALE
				));
		}
	}

	private User createUser(final String loginId, final String email, final String birthDate) {
		final User user = new User(
			new LoginId(loginId),
			new Email(email),
			new BirthDate(birthDate),
			Gender.MALE
		);

		return userRepository.save(user);
	}
}
