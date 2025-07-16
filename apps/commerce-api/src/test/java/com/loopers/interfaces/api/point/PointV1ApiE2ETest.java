package com.loopers.interfaces.api.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.loopers.domain.point.Point;
import com.loopers.infrastructure.point.PointJpaRepository;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PointV1ApiE2ETest {

	private final TestRestTemplate testRestTemplate;
	private final PointJpaRepository pointJpaRepository;
	private final DatabaseCleanUp databaseCleanUp;

	@Autowired

	public PointV1ApiE2ETest(
		final TestRestTemplate testRestTemplate,
		final PointJpaRepository pointJpaRepository,
	 	final DatabaseCleanUp databaseCleanUp
	) {
		this.testRestTemplate = testRestTemplate;
		this.pointJpaRepository = pointJpaRepository;
		this.databaseCleanUp = databaseCleanUp;
	}

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("GET /api/v1/points")
	@Nested
	class GET {

		@DisplayName("포인트 조회에 성공하면, 포인트 정보를 반환한다.")
		@Test
		void returnsPointInfo_whenGetPoint() {
			final Point point = createPoint(1L, 1L);
			final HttpHeaders headers = new HttpHeaders();
			headers.set("X-USER-ID", point.getUserId().toString());
			final ParameterizedTypeReference<ApiResponse<PointV1Dto.PointResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<PointV1Dto.PointResponse>> actual =
				testRestTemplate.exchange("/api/v1/points", HttpMethod.GET, new HttpEntity<>(headers), responseType);

			assertAll(
				() -> assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue(),
				() -> assertThat(actual.getBody().data().id()).isEqualTo(point.getId())
			);
		}

		@DisplayName("존재하지 않는 ID로 조회하면, NOT_FOUND 예외가 반환된다.")
		@Test
		void throwsNotFoundException_whenPointNonExists() {
			final HttpHeaders headers = new HttpHeaders();
			headers.set("X-USER-ID", "-1");
			final ParameterizedTypeReference<ApiResponse<PointV1Dto.PointResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<PointV1Dto.PointResponse>> actual =
				testRestTemplate.exchange("/api/v1/points", HttpMethod.GET, new HttpEntity<>(headers), responseType);

			assertAll(
				() -> assertTrue(actual.getStatusCode().is4xxClientError()),
				() -> assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
			);
		}
	}

	private Point createPoint(final Long userId, final Long amount) {
		final Point point = new Point(userId, amount);

		return pointJpaRepository.save(point);
	}
}
