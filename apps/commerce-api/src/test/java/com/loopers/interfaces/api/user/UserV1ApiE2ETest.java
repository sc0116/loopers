package com.loopers.interfaces.api.user;

import static com.loopers.interfaces.api.user.UserDto.V1.GetResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.loopers.domain.user.BirthDate;
import com.loopers.domain.user.Email;
import com.loopers.domain.user.Gender;
import com.loopers.domain.user.LoginId;
import com.loopers.domain.user.User;
import com.loopers.infrastructure.user.UserJpaRepository;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.interfaces.api.user.UserDto.V1.RegisterRequest;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
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
class UserV1ApiE2ETest {

	private final TestRestTemplate testRestTemplate;
	private final UserJpaRepository userJpaRepository;
	private final DatabaseCleanUp databaseCleanUp;

	@Autowired
	public UserV1ApiE2ETest(
		TestRestTemplate testRestTemplate,
		UserJpaRepository userJpaRepository,
		DatabaseCleanUp databaseCleanUp
	) {
		this.testRestTemplate = testRestTemplate;
		this.userJpaRepository = userJpaRepository;
		this.databaseCleanUp = databaseCleanUp;
	}

	@AfterEach
	void tearDown() {
		databaseCleanUp.truncateAllTables();
	}

	@DisplayName("POST /api/v1/users")
	@Nested
	class POST {

		@DisplayName("정상적으로 회원 가입하면, 유저 정보를 반환한다.")
		@Test
		void returnsUserInfo_whenRegister() {
			final RegisterRequest request = new RegisterRequest(
				"jjanggu",
				"jjanggu@gmail.com",
				"2025-01-01",
				Gender.MALE.name()
			);
			final ParameterizedTypeReference<ApiResponse<GetResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<GetResponse>> actual =
				testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, new HttpEntity<>(request), responseType);

			assertAll(
				() -> assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue(),
				() -> assertThat(actual.getBody().data().id()).isNotEqualTo(0L)
			);
		}

		@DisplayName("회원 가입 시 성별이 비어있으면, BAD_REQUEST 예외가 반환된다.")
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenGenderIsNullOrBlank(final String gender) {
			final RegisterRequest request = new RegisterRequest(
				"jjanggu",
				"jjanggu@gmail.com",
				"2025-01-01",
				gender
			);
			final ParameterizedTypeReference<ApiResponse<GetResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<GetResponse>> actual =
				testRestTemplate.exchange("/api/v1/users", HttpMethod.POST, new HttpEntity<>(request), responseType);

			assertAll(
				() -> assertTrue(actual.getStatusCode().is4xxClientError()),
				() -> assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
			);
		}
	}

	@DisplayName("/api/v1/users/me")
	@Nested
	class GET {

		@DisplayName("내 정보 조회에 성공하면, 해당 유저 정보를 반환한다.")
		@Test
		void returnUserInfo_whenGetMe() {
			final User user = createUser("jjanggu", "jjanggu@gmail.com", "2025-01-01");
			final HttpHeaders headers = new HttpHeaders();
			headers.set("X-USER-ID", user.getId().toString());
			final ParameterizedTypeReference<ApiResponse<GetResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<GetResponse>> actual =
				testRestTemplate.exchange("/api/v1/users/me", HttpMethod.GET, new HttpEntity<>(headers), responseType);

			assertAll(
				() -> assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue(),
				() -> assertThat(actual.getBody().data().id()).isEqualTo(user.getId())
			);
		}

		@DisplayName("존재하지 않는 ID로 조회하면, NOT_FOUND 예외가 반환된다.")
		@Test
		void throwsNotFoundException_whenUserNonExists() {
			final HttpHeaders headers = new HttpHeaders();
			headers.set("X-USER-ID", "-1");
			final ParameterizedTypeReference<ApiResponse<GetResponse>> responseType = new ParameterizedTypeReference<>() {};

			final ResponseEntity<ApiResponse<GetResponse>> actual =
				testRestTemplate.exchange("/api/v1/users/me", HttpMethod.GET, new HttpEntity<>(headers), responseType);

			assertAll(
				() -> assertTrue(actual.getStatusCode().is4xxClientError()),
				() -> assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
			);
		}
	}

	private User createUser(final String loginId, final String email, final String birthDate) {
		final User user = new User(new LoginId(loginId), new Email(email), new BirthDate(birthDate), Gender.MALE);

		return userJpaRepository.save(user);
	}
}
