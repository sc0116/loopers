package com.loopers.application.user;

import com.loopers.domain.user.UserInfo;
import com.loopers.domain.user.UserService;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;

    public UserResult register(final UserCriteria.Register criteria) {
        return UserResult.from(userService.create(criteria.toCommand()));
    }

	public UserResult getUser(final Long userId) {
		final UserInfo userInfo = userService.getUser(userId);

		if (userInfo == null) {
			throw new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 회원입니다.");
		}

		return UserResult.from(userInfo);
	}
}
