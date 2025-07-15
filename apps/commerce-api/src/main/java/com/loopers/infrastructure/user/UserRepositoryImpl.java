package com.loopers.infrastructure.user;

import com.loopers.domain.user.Email;
import com.loopers.domain.user.LoginId;
import com.loopers.domain.user.User;
import com.loopers.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public User save(final User user) {
		return userJpaRepository.save(user);
	}

	@Override
	public boolean existsBy(final LoginId loginId) {
		return userJpaRepository.existsByLoginId(loginId);
	}

	@Override
	public boolean existsBy(final Email email) {
		return userJpaRepository.existsByEmail(email);
	}
}
