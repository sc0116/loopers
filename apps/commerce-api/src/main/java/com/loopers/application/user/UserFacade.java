package com.loopers.application.user;

import com.loopers.domain.point.PointCommand;
import com.loopers.domain.point.PointService;
import com.loopers.domain.user.UserInfo;
import com.loopers.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;
	private final PointService pointService;

    public UserResult register(final UserCriteria.Register criteria) {
		final UserResult userResult = UserResult.from(userService.register(criteria.toCommand()));

		pointService.create(new PointCommand.Create(userResult.id(), 0L));

		return userResult;
    }

	public UserResult getUser(final Long userId) {
		final UserInfo userInfo = userService.get(userId);

		return UserResult.from(userInfo);
	}
}
