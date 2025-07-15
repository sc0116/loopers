package com.loopers.domain.user;

public interface UserRepository {

	User save(User user);

	boolean existsBy(LoginId loginId);

	boolean existsBy(Email email);
}
