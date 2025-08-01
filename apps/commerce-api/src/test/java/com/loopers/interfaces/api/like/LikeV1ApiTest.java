package com.loopers.interfaces.api.like;

import com.loopers.application.like.ProductLikeFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeV1Controller.class)
public class LikeV1ApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ProductLikeFacade productLikeFacade;

	@DisplayName("GET /api/v1/like/products")
	@Nested
	class GetMyProducts {

		@DisplayName("X-USER-ID가 누락되면, BAD_REQUEST 예외가 반환된다.")
		@Test
		void throwsBadRequestException_whenRequestHeaderIsMissing() throws Exception {
			mockMvc.perform(get("/api/v1/like/products"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.meta.message").value("필수 헤더 'X-USER-ID' 가 누락되었습니다."));
		}
	}

	@DisplayName("POST /api/v1/like/products/{productId}")
	@Nested
	class PostLike {

		@DisplayName("X-USER-ID가 누락되면, BAD_REQUEST 예외가 반환된다.")
		@Test
		void throwsBadRequestException_whenRequestHeaderIsMissing() throws Exception {
			mockMvc.perform(post("/api/v1/like/products/1"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.meta.message").value("필수 헤더 'X-USER-ID' 가 누락되었습니다."));
		}
	}

	@DisplayName("DELETE /api/v1/like/products/{productId}")
	@Nested
	class Unlike {

		@DisplayName("X-USER-ID가 누락되면, BAD_REQUEST 예외가 반환된다.")
		@Test
		void throwsBadRequestException_whenRequestHeaderIsMissing() throws Exception {
			mockMvc.perform(delete("/api/v1/like/products/1"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.meta.message").value("필수 헤더 'X-USER-ID' 가 누락되었습니다."));
		}
	}
}
