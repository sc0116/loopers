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
	public UserInfo create(final UserCommand.Create command) {
		final User user = command.toUser();

		if (userRepository.existsBy(user.getLoginId())) {
			throw new CoreException(ErrorType.CONFLICT, "이미 존재하는 ID입니다.");
		}

		if (userRepository.existsBy(user.getEmail())) {
			throw new CoreException(ErrorType.CONFLICT, "이미 존재하는 이메일입니다.");
		}

		return UserInfo.from(userRepository.save(user));
	}
}
