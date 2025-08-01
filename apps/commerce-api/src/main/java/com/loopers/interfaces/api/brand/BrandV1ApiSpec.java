package com.loopers.interfaces.api.brand;

import com.loopers.interfaces.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Brand V1 API", description = "Brand V1 API 입니다.")
public interface BrandV1ApiSpec {

	@Operation(
		summary = "브랜드 정보 조회"
	)
	ApiResponse<BrandDto.V1.GetBrandResponse> getBrand(
		@Schema(name = "브랜드 ID")
		Long brandId
	);
}
