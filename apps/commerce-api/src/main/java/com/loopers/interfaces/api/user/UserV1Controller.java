package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserFacade;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.user.UserDto.V1.GetResponse;
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

    @PostMapping
    @Override
    public ApiResponse<GetResponse> register(
        @RequestBody final UserDto.V1.RegisterRequest request
    ) {
		final GetResponse response = GetResponse.from(userFacade.register(request.toCriteria()));

		return ApiResponse.success(response);
    }

	@GetMapping("/me")
	@Override
	public ApiResponse<GetResponse> getMe(
		@RequestHeader("X-USER-ID") final Long userId
	) {
		final GetResponse response = GetResponse.from(userFacade.getUser(userId));

		return ApiResponse.success(response);
	}
}
