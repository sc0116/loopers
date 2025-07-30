package com.loopers.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ProductServiceIntegrationTest {

	@Autowired
	private ProductService sut;

	@Autowired
	private ProductRepository productRepository;

	@DisplayName("상품 정보를 조회할 때, ")
	@Nested
	class GetProduct {

		@DisplayName("존재하는 상품 ID가 주어지면, 상품 정보를 반환한다.")
		@Test
		void returnBrandInfo_whenProductAlreadyExists() {
			final Product product = createProduct(1L, "짱구", "짱구는 못말립니다.", 100L, 1);

			final ProductInfo actual = sut.getProduct(new ProductCommand.GetProduct(product.getId()));

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new ProductInfo(
					product.getId(),
					new BrandId(1L),
					"짱구",
					"짱구는 못말립니다.",
					BigDecimal.valueOf(100L),
					new StockQuantity(1)
				));
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

		return productRepository.save(product);
	}
}
