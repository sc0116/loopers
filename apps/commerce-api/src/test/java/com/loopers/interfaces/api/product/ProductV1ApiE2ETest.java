package com.loopers.interfaces.api.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.loopers.domain.product.BrandId;
import com.loopers.domain.product.Product;
import com.loopers.domain.product.StockQuantity;
import com.loopers.infrastructure.product.ProductJpaRepository;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.utils.DatabaseCleanUp;
import java.math.BigDecimal;
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
class ProductV1ApiE2ETest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ProductJpaRepository productJpaRepository;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("GET /api/v1/products/{productId}")
	@Nested
	class Get {

		@DisplayName("상품 정보 조회에 성공하면, 상품 정보를 반환한다.")
		@Test
		void returnsProductResponse_whenGetProduct() {
			final Product product = createProduct(1L, "짱구", "짱구는 못말립니다.", 100L, 1);
			final ParameterizedTypeReference<ApiResponse<ProductDto.V1.GetResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<ProductDto.V1.GetResponse>> actual =
				testRestTemplate.exchange("/api/v1/products/" + product.getId(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), responseType);

			assertAll(
				() -> assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue(),
				() -> assertThat(actual.getBody().data()).usingRecursiveComparison()
					.withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
					.isEqualTo(new ProductDto.V1.GetResponse(
						product.getId(),
						product.getBrandId().getBrandId(),
						"짱구",
						"짱구는 못말립니다.",
						BigDecimal.valueOf(100L),
						1
					))
			);
		}
	}

	private Product createProduct(
		final Long brandId,
		final String name,
		final String description,
		final Long price,
		final Integer stockQuantity
	) {
		final Product product = new Product(
			new BrandId(brandId),
			name,
			description,
			BigDecimal.valueOf(price),
			new StockQuantity(stockQuantity)
		);

		return productJpaRepository.save(product);
	}
}
