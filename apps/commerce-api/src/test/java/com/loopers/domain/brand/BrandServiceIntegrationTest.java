package com.loopers.domain.brand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class BrandServiceIntegrationTest {

	@Autowired
	private BrandService sut;

	@Autowired
	private BrandRepository brandRepository;

	@DisplayName("브랜드 정보를 조회할 때, ")
	@Nested
	class Get {

		@DisplayName("존재하는 브랜드 ID가 주어지면, 브랜드 정보를 반환한다.")
		@Test
		void returnBrandInfo_whenBrandAlreadyExists() {
			final Brand brand = createBrand("짱구", "짱구는 못말립니다.");

			final BrandInfo actual = sut.get(new BrandCommand.Get(brand.getId()));

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new BrandInfo(
					brand.getId(),
					"짱구",
					"짱구는 못말립니다."
				));
		}
	}

	private Brand createBrand(final String name, final String description) {
		final Brand brand = new Brand(name, description);

		return brandRepository.save(brand);
	}
}
