package com.loopers.interfaces.api.brand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.loopers.domain.brand.Brand;
import com.loopers.infrastructure.brand.BrandJpaRepository;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BrandV1ApiE2ETest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private BrandJpaRepository brandJpaRepository;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("GET /api/v1/brands/{brandId}")
	@Nested
	class Get {

		@DisplayName("브랜드 정보 조회에 성공하면, 브랜드 정보를 반환한다.")
		@Test
		void returnsBrandResponse_whenGetBrand() {
			final Brand brand = createBrand("짱구", "짱구는 못말립니다.");
			final ParameterizedTypeReference<ApiResponse<BrandDto.V1.GetBrandResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<BrandDto.V1.GetBrandResponse>> actual =
				testRestTemplate.exchange("/api/v1/brands/" + brand.getId(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), responseType);

			assertAll(
				() -> assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue(),
				() -> assertThat(actual.getBody().data()).usingRecursiveComparison()
					.isEqualTo(new BrandDto.V1.GetBrandResponse(brand.getId(), "짱구", "짱구는 못말립니다."))
			);
		}
	}

	private Brand createBrand(final String name, final String description) {
		final Brand brand = new Brand(name, description);

		return brandJpaRepository.save(brand);
	}
}
