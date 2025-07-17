package com.loopers.interfaces.api.point;

import com.loopers.interfaces.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Point V1 API", description = "Point V1 API 입니다.")
public interface PointV1ApiSpec {

	@Operation(
		summary = "포인트 충전"
	)
	ApiResponse<?> chargePoint(
		@Schema(name = "X-USER-ID")
		Long userId,
		@Schema(name = "충전 금액 요청")
		PointV1Dto.PointChargeRequest request
	);

	@Operation(
		summary = "포인트 조회"
	)
	ApiResponse<?> getPoint(
		@Schema(name = "X-USER-ID")
		Long userId
	);
}
