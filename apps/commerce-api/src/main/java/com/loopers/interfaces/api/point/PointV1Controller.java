package com.loopers.interfaces.api.point;

import com.loopers.domain.point.PointCommand;
import com.loopers.domain.point.PointInfo;
import com.loopers.domain.point.PointService;
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
@RequestMapping("/api/v1/points")
public class PointV1Controller implements PointV1ApiSpec {

	private final PointService pointService;

	@GetMapping
	@Override
	public ApiResponse<PointDto.V1.GetPointResponse> getPoint(
		@RequestHeader("X-USER-ID") final Long userId
	) {
		final PointInfo pointInfo = pointService.getPoint(new PointCommand.GetPoint(userId));

		return ApiResponse.success(PointDto.V1.GetPointResponse.from(pointInfo));
	}

	@PostMapping("/charge")
	@Override
	public ApiResponse<PointDto.V1.GetPointResponse> charge(
		@RequestHeader("X-USER-ID") final Long userId,
		@RequestBody final PointDto.V1.ChargeRequest request
	) {
		final PointInfo pointInfo = pointService.charge(request.toCommand(userId));

		return ApiResponse.success(PointDto.V1.GetPointResponse.from(pointInfo));
	}
}
