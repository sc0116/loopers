package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserFacade;
import com.loopers.application.user.UserResult;
import com.loopers.domain.user.UserCommand;
import com.loopers.domain.user.UserInfo;
import com.loopers.domain.user.UserService;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserV1Controller implements UserV1ApiSpec {

    private final UserFacade userFacade;
	private final UserService userService;

    @PostMapping
    @Override
    public ApiResponse<UserDto.V1.GetUserResponse> register(
        @RequestBody final UserDto.V1.RegisterRequest request
    ) {
		final UserResult userResult = userFacade.register(request.toCriteria());

		return ApiResponse.success(UserDto.V1.GetUserResponse.from(userResult));
    }

	@GetMapping("/me")
	@Override
	public ApiResponse<UserDto.V1.GetUserResponse> getMe(
		@RequestHeader("X-USER-ID") final Long userId
	) {
		final UserInfo userInfo = userService.getUser(new UserCommand.GetUser(userId));

		return ApiResponse.success(UserDto.V1.GetUserResponse.from(userInfo));
	}
}
