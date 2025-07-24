package com.loopers.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
public class UserServiceTest {

	@InjectMocks
	private UserService sut;

	@Mock
	private UserRepository userRepository;

	@DisplayName("유저를 생성할 때, ")
	@Nested
	class Create {
		@DisplayName("이미 존재하는 ID가 주어지면, CONFLICT 예외가 발생한다.")
		@Test
		void throwsConflictException_whenIdAlreadyExists() {
			given(userRepository.existsBy(any(LoginId.class)))
				.willReturn(true);

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.register(new UserCommand.Register("jjanggu", "jjanggu@naver.com", "2025-01-01", Gender.MALE.name()));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.CONFLICT, "이미 존재하는 ID입니다."));
		}

		@DisplayName("이미 존재하는 이메일이 주어지면, CONFLICT 예외가 발생한다.")
		@Test
		void throwsConflictException_whenEmailAlreadyExists() {
			given(userRepository.existsBy(any(LoginId.class)))
				.willReturn(false);
			given(userRepository.existsBy(any(Email.class)))
				.willReturn(true);

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.register(new UserCommand.Register("jjanggu", "jjanggu@naver.com", "2025-01-01", Gender.MALE.name()));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.CONFLICT, "이미 존재하는 이메일입니다."));
		}
	}

	@DisplayName("유저 정보 조회할 때, ")
	@Nested
	class Read {

		@DisplayName("존재하지 않는 회원 ID가 주어지면, NOT_FOUND 예외를 반환한다.")
		@Test
		void returnThrows_whenUserNonExists() {
			given(userRepository.findById(anyLong()))
				.willReturn(Optional.empty());

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.get(-1L);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 회원입니다."));
		}
	}
}
