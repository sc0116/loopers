package com.loopers.domain.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserInfo register(final UserCommand.Register command) {
		final User user = new User(
			new LoginId(command.loginId()),
			new Email(command.email()),
			new BirthDate(command.birthDate()),
			Gender.from(command.gender())
		);

		if (userRepository.existsBy(user.getLoginId())) {
			throw new CoreException(ErrorType.CONFLICT, "이미 존재하는 ID입니다.");
		}
		if (userRepository.existsBy(user.getEmail())) {
			throw new CoreException(ErrorType.CONFLICT, "이미 존재하는 이메일입니다.");
		}

		return UserInfo.from(userRepository.save(user));
	}

	@Transactional(readOnly = true)
	public UserInfo getUser(final UserCommand.GetUser command) {
		return userRepository.findBy(command.id())
			.map(UserInfo::from)
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 회원입니다."));
	}
}
