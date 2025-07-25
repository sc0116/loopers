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
	ApiResponse<PointDto.V1.GetResponse> chargePoint(
		@Schema(name = "X-USER-ID")
		Long userId,
		@Schema(name = "충전 금액 요청")
		PointDto.V1.ChargeRequest request
	);

	@Operation(
		summary = "포인트 조회"
	)
	ApiResponse<PointDto.V1.GetResponse> getPoint(
		@Schema(name = "X-USER-ID")
		Long userId
	);
}
