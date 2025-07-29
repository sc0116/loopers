package com.loopers.interfaces.api.like;

import com.loopers.domain.brand.Brand;
import com.loopers.domain.product.BrandId;
import com.loopers.domain.product.Product;
import com.loopers.domain.product.StockQuantity;
import com.loopers.domain.user.*;
import com.loopers.infrastructure.brand.BrandJpaRepository;
import com.loopers.infrastructure.product.ProductJpaRepository;
import com.loopers.infrastructure.user.UserJpaRepository;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikeV1ApiE2ETest {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private BrandJpaRepository brandJpaRepository;

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

	@DisplayName("POST /api/v1/like/products/{productId}")
	@Nested
	class PostLike {

		@DisplayName("회원이 상품을 좋아요하면, 204 NO_CONTENT 응답을 받는다.")
		@Test
		void returnNoContent_whenUserLikesProduct() {
			final User user = create("jjanggu", "jjanggu@gmail.com", "2000-01-01");
			final Brand brand = create("투니버스", "짱구는 못말려");
			final Product product = create(brand, "짱구", "신짱구입니다.", BigDecimal.TEN, 1);

			final ResponseEntity<Object> actual = restTemplate.postForEntity(
				"/api/v1/like/products/" + product.getId(),
				new HttpEntity<>(xUserId(user.getId())),
				Object.class
			);

			assertTrue(actual.getStatusCode().is2xxSuccessful());
		}

	}

	private User create(final String loginId, final String email, final String birthDate) {
		final User user = new User(new LoginId(loginId), new Email(email), new BirthDate(birthDate), Gender.MALE);

		return userJpaRepository.save(user);
	}

	private Brand create(final String name, final String description) {
		final Brand brand = new Brand(name, description);

		return brandJpaRepository.save(brand);
	}

	private Product create(
		final Brand brand,
		final String name,
		final String description,
		final BigDecimal price,
		final Integer stockQuantity
	) {
		final Product product = new Product(
			new BrandId(brand.getId()),
			name,
			description,
			price,
			new StockQuantity(stockQuantity)
		);

		return productJpaRepository.save(product);
	}

	private HttpHeaders xUserId(final Long userId) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("X-USER-ID", userId.toString());
		return httpHeaders;
	}
}
