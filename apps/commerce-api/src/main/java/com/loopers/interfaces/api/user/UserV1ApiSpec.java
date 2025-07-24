package com.loopers.interfaces.api.user;

import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.user.UserDto.V1.GetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User V1 API", description = "User V1 API 입니다.")
public interface UserV1ApiSpec {

    @Operation(
        summary = "회원가입"
    )
	ApiResponse<GetResponse> register(
		@Schema(name = "회원가입 요청")
		UserDto.V1.RegisterRequest request
	);

	@Operation(
		summary = "내 정보 조회"
	)
	ApiResponse<GetResponse> getMe(
		@Schema(name = "X-USER-ID")
		Long userId
	);
}
