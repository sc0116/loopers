package com.loopers.interfaces.api.point;

import com.loopers.application.point.PointFacade;
import com.loopers.application.point.PointResult;
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

    private final PointFacade pointFacade;

	@PostMapping("/charge")
	@Override
	public ApiResponse<PointDto.V1.GetResponse> chargePoint(
		@RequestHeader("X-USER-ID") final Long userId,
		@RequestBody final PointDto.V1.ChargeRequest request
	) {
		final PointResult pointResult = pointFacade.charge(request.toCriteria(userId));

		return ApiResponse.success(PointDto.V1.GetResponse.from(pointResult));
	}

	@GetMapping
	@Override
	public ApiResponse<PointDto.V1.GetResponse> getPoint(
		@RequestHeader("X-USER-ID") final Long userId
	) {
		final PointResult pointResult = pointFacade.getPoint(userId);

		return ApiResponse.success(PointDto.V1.GetResponse.from(pointResult));
	}
}
