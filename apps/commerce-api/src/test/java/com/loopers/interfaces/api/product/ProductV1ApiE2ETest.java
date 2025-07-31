package com.loopers.interfaces.api.product;

import static com.loopers.domain.count.ProductCount.CountType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.loopers.domain.brand.Brand;
import com.loopers.domain.count.ProductCount;
import com.loopers.domain.product.BrandId;
import com.loopers.domain.product.Product;
import com.loopers.domain.stock.ProductStock;
import com.loopers.domain.stock.StockQuantity;
import com.loopers.infrastructure.brand.BrandJpaRepository;
import com.loopers.infrastructure.count.ProductCountJpaRepository;
import com.loopers.infrastructure.product.ProductJpaRepository;
import com.loopers.infrastructure.stock.ProductStockJpaRepository;
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
	private BrandJpaRepository brandJpaRepository;

	@Autowired
	private ProductStockJpaRepository productStockJpaRepository;

	@Autowired
	private ProductCountJpaRepository productCountJpaRepository;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("GET /api/v1/products/{productId}")
	@Nested
	class GetProduct {

		@DisplayName("상품 정보 조회에 성공하면, 상품 정보를 반환한다.")
		@Test
		void returnsProductResponse_whenGetProduct() {
			final Brand brand = create("브랜드명", "브랜드 설명");
			final Product product = createProduct(brand.getId(), "짱구", "짱구는 못말립니다.", 100L);
			final ProductStock productStock = create(product, 10);
			final ProductCount productCount = create(product, 5L);
			final ParameterizedTypeReference<ApiResponse<ProductDto.V1.GetResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<ProductDto.V1.GetResponse>> actual =
				testRestTemplate.exchange("/api/v1/products/" + product.getId(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), responseType);

			assertAll(
				() -> assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue(),
				() -> assertThat(actual.getBody().data()).usingRecursiveComparison()
					.withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
					.isEqualTo(new ProductDto.V1.GetResponse(
						product.getId(),
						"짱구",
						"짱구는 못말립니다.",
						BigDecimal.valueOf(100L),
						10,
						5L,
						brand.getId(),
						"브랜드명",
						"브랜드 설명"
					))
			);
		}
	}

	private Product createProduct(
		final Long brandId,
		final String name,
		final String description,
		final Long price
	) {
		final Product product = new Product(
			new BrandId(brandId),
			name,
			description,
			BigDecimal.valueOf(price)
		);

		return productJpaRepository.save(product);
	}

	private Brand create(final String name, final String description) {
		final Brand brand = new Brand(name, description);

		return brandJpaRepository.save(brand);
	}

	private ProductStock create(final Product product, final Integer quantity) {
		final ProductStock productStock = new ProductStock(product.getId(), new StockQuantity(quantity));

		return productStockJpaRepository.save(productStock);
	}

	private ProductCount create(final Product product, final Long likeCount) {
		final ProductCount productCount = new ProductCount(product.getId());

		for (long i = 0; i < likeCount; i++) {
			productCount.increment(CountType.LIKE);
		}

		return productCountJpaRepository.save(productCount);
	}
}
