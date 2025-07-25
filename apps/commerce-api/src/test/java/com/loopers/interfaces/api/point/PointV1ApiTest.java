package com.loopers.interfaces.api.point;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.loopers.application.point.PointFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PointV1Controller.class)
public class PointV1ApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private PointFacade pointFacade;

	@DisplayName("POST /api/v1/points/charge")
	@Nested
	class Charge {

		@DisplayName("X-USER-ID가 누락되면, BAD_REQUEST 예외가 반환된다.")
		@Test
		void throwsBadRequestException_whenRequestHeaderIsMissing() throws Exception {
			mockMvc.perform(post("/api/v1/points/charge"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.meta.message").value("필수 헤더 'X-USER-ID' 가 누락되었습니다."));
		}
	}

	@DisplayName("GET /api/v1/points")
	@Nested
	class GET {

		@DisplayName("X-USER-ID가 누락되면, BAD_REQUEST 예외가 반환된다.")
		@Test
		void throwsBadRequestException_whenRequestHeaderIsMissing() throws Exception {
			mockMvc.perform(get("/api/v1/points"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.meta.message").value("필수 헤더 'X-USER-ID' 가 누락되었습니다."));
		}
	}
}
