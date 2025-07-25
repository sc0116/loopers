package com.loopers.domain.user;

import java.util.Optional;

public interface UserRepository {

	User save(User user);

	boolean existsBy(LoginId loginId);

	boolean existsBy(Email email);

	Optional<User> findById(Long id);
}
