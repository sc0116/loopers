package com.loopers.interfaces.api.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.loopers.domain.order.Order;
import com.loopers.domain.order.OrderItem;
import com.loopers.domain.point.Amount;
import com.loopers.domain.point.Point;
import com.loopers.domain.product.BrandId;
import com.loopers.domain.product.Product;
import com.loopers.domain.stock.ProductStock;
import com.loopers.domain.stock.StockQuantity;
import com.loopers.infrastructure.order.OrderJpaRepository;
import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.infrastructure.product.ProductJpaRepository;
import com.loopers.infrastructure.stock.ProductStockJpaRepository;
import com.loopers.interfaces.api.ApiResponse;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderV1ApiE2ETest {

	@Autowired
	private OrderJpaRepository orderJpaRepository;

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
			final Product product = defaultProduct("상품명");
			createProductStock(product, 10);
			createPoint(1L, 55L);

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

	@DisplayName("GET /api/v1/orders/{orderId}")
	@Nested
	class GetOrder {

		@DisplayName("주문 상세 정보 조회에 성공하면, 주문 상세 정보를 반환한다.")
		@Test
		void returnsOrderResponse_whenGetOrder() {
			final Product product = defaultProduct("상품명");
			final Order order = create(1L, List.of(create(product, 2, BigDecimal.valueOf(1500))));

			final ParameterizedTypeReference<ApiResponse<OrderDto.V1.GetOrderDetailResponse>> responseType = new ParameterizedTypeReference<>() {};
			final ResponseEntity<ApiResponse<OrderDto.V1.GetOrderDetailResponse>> actual = restTemplate.exchange(
				"/api/v1/orders/" + order.getId(),
				HttpMethod.GET,
				new HttpEntity<>(xUserId(1L)),
				responseType
			);

			assertAll(
				() -> assertTrue(actual.getStatusCode().is2xxSuccessful()),
				() -> assertThat(actual.getBody().data()).usingRecursiveComparison()
					.isEqualTo(new OrderDto.V1.GetOrderDetailResponse(
						order.getId(),
						"PENDING",
						new BigDecimal("3000.00"),
						List.of(new OrderDto.V1.GetOrderDetailResponse.GetOrderItemDetailResponse(
							product.getId(),
							"상품명",
							2,
							new BigDecimal("1500.00")
						))
					))
			);
		}
	}

	private Order create(final Long userId, List<OrderItem> items) {
		final Order order = new Order(userId, items);

		return orderJpaRepository.save(order);
	}

	private OrderItem create(final Product product, final Integer quantity, final BigDecimal price) {
		return new OrderItem(product.getId(), quantity, price);
	}

	private Product defaultProduct(final String name) {
		final Product product = new Product(
			new BrandId(1L),
			name,
			"상품 설명",
			BigDecimal.TEN
		);

		return productJpaRepository.save(product);
	}

	private ProductStock createProductStock(final Product product, final Integer quantity) {
		final ProductStock productStock = new ProductStock(product.getId(), new StockQuantity(quantity));

		return productStockJpaRepository.save(productStock);
	}

	private Point createPoint(final Long userId, final Long amount) {
		final Point point = new Point(userId, new Amount(amount));

		return pointJpaRepository.save(point);
	}

	private HttpHeaders xUserId(final Long userId) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-USER-ID", userId.toString());
		return httpHeaders;
	}
}
