package com.loopers.interfaces.api.order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.loopers.domain.point.Amount;
import com.loopers.domain.point.Point;
import com.loopers.domain.product.BrandId;
import com.loopers.domain.product.Product;
import com.loopers.domain.stock.ProductStock;
import com.loopers.domain.stock.StockQuantity;
import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.infrastructure.product.ProductJpaRepository;
import com.loopers.infrastructure.stock.ProductStockJpaRepository;
import com.loopers.utils.DatabaseCleanUp;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderV1ApiE2ETest {

	@Autowired
	private ProductJpaRepository productJpaRepository;

	@Autowired
	private ProductStockJpaRepository productStockJpaRepository;

	@Autowired
	private PointJpaRepository pointJpaRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("POST /api/v1/orders")
	@Nested
	class PostOrder {

		@DisplayName("주문을 요청하면, 200 OK 응답을 받는다.")
		@Test
		void returnOk_whenOrderRequest() {
			final Product product = defaultProduct();
			final ProductStock productStock = create(product, 10);
			final Point point = create(1L, 55L);

			final OrderDto.V1.OrderRequest.OrderItemRequest item = new OrderDto.V1.OrderRequest.OrderItemRequest(product.getId(), 5);
			final OrderDto.V1.OrderRequest request = new OrderDto.V1.OrderRequest(List.of(item));

			final ResponseEntity<Object> actual = restTemplate.exchange(
				"/api/v1/orders",
				HttpMethod.POST,
				new HttpEntity<>(request, xUserId(1L)),
				Object.class
			);

			assertTrue(actual.getStatusCode().is2xxSuccessful());
		}
	}

	private Product defaultProduct() {
		final Product product = new Product(
			new BrandId(1L),
			"상품명",
			"상품 설명",
			BigDecimal.TEN
		);

		return productJpaRepository.save(product);
	}

	private ProductStock create(final Product product, final Integer quantity) {
		final ProductStock productStock = new ProductStock(product.getId(), new StockQuantity(quantity));

		return productStockJpaRepository.save(productStock);
	}

	private Point create(final Long userId, final Long amount) {
		final Point point = new Point(userId, new Amount(amount));

		return pointJpaRepository.save(point);
	}

	private HttpHeaders xUserId(final Long userId) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-USER-ID", userId.toString());
		return httpHeaders;
	}
}
