package com.loopers.interfaces.api.like;

import static com.loopers.domain.like.LikeTarget.TargetType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.loopers.domain.like.Like;
import com.loopers.domain.like.LikeTarget;
import com.loopers.domain.product.BrandId;
import com.loopers.domain.product.Product;
import com.loopers.infrastructure.like.LikeJpaRepository;
import com.loopers.infrastructure.product.ProductJpaRepository;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.like.LikeDto.V1.GetMyProductsResponse.GetMyProductResponse;
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
public class LikeV1ApiE2ETest {

	@Autowired
	private LikeJpaRepository likeJpaRepository;

	@Autowired
	private ProductJpaRepository productJpaRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private DatabaseCleanUp databaseCleanUp;

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("GET /api/v1/like/products")
	@Nested
	class GetMyProducts {

		@DisplayName("내가 좋아요한 상품 목록 조회에 성공하면, 상품 목록 정보를 반환한다.")
		@Test
		void returnMyLikeProductsInfo_whenGetUserId() {
			final Product product = defaultProduct();
			final Like like = likeProduct(1L, product.getId());

			final ParameterizedTypeReference<ApiResponse<LikeDto.V1.GetMyProductsResponse>> responseType = new ParameterizedTypeReference<>() {};
			final ResponseEntity<ApiResponse<LikeDto.V1.GetMyProductsResponse>> actual = restTemplate.exchange(
				"/api/v1/like/products",
				HttpMethod.GET,
				new HttpEntity<>(xUserId(1L)),
				responseType
			);

			assertAll(
				() -> assertTrue(actual.getStatusCode().is2xxSuccessful()),
				() -> assertThat(actual.getBody().data()).usingRecursiveComparison()
					.isEqualTo(new LikeDto.V1.GetMyProductsResponse(List.of(
						new GetMyProductResponse(product.getId(), product.getName(), product.getDescription(), new BigDecimal("10.00"))
					)))
			);
		}
	}

	@DisplayName("POST /api/v1/like/products/{productId}")
	@Nested
	class PostLike {

		@DisplayName("회원이 상품을 좋아요하면, 200 Ok 응답을 받는다.")
		@Test
		void returnOk_whenUserLikesProduct() {
			final Product product = defaultProduct();

			final ResponseEntity<Object> actual = restTemplate.postForEntity(
				"/api/v1/like/products/" + product.getId(),
				new HttpEntity<>(xUserId(1L)),
				Object.class
			);

			assertTrue(actual.getStatusCode().is2xxSuccessful());
		}

	}

	@DisplayName("DELETE /api/v1/like/products/{productId}")
	@Nested
	class Unlike {

		@DisplayName("회원이 상품을 좋아요 취소하면, 200 OK 응답을 받는다.")
		@Test
		void returnOk_whenUserUnlikesProduct() {
			final Product product = defaultProduct();
			likeProduct(1L, product.getId());

			final ResponseEntity<Object> actual = restTemplate.exchange(
				"/api/v1/like/products/" + product.getId(),
				HttpMethod.DELETE,
				new HttpEntity<>(xUserId(1L)),
				Object.class
			);

			assertTrue(actual.getStatusCode().is2xxSuccessful());
		}

	}

	private Like likeProduct(final Long userId, final Long productId) {
		final Like like = new Like(userId, new LikeTarget(TargetType.PRODUCT, productId));

		return likeJpaRepository.save(like);
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

	private HttpHeaders xUserId(final Long userId) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-USER-ID", userId.toString());
		return httpHeaders;
	}
}
