package com.loopers.application.user;

import com.loopers.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserService userService;

    public UserResult register(final UserCriteria.Register criteria) {
        return UserResult.from(userService.create(criteria.toCommand()));
    }
}
