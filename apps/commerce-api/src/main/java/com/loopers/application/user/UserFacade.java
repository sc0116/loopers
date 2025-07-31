package com.loopers.application.user;

import com.loopers.domain.point.PointCommand;
import com.loopers.domain.point.PointService;
import com.loopers.domain.user.UserInfo;
import com.loopers.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;
	private final PointService pointService;

	@Transactional
    public UserResult register(final UserCriteria.Register criteria) {
		final UserInfo userInfo = userService.register(criteria.toCommand());

		pointService.create(new PointCommand.Create(userInfo.id(), 0L));

		return UserResult.from(userInfo);
    }
}
