package com.loopers.infrastructure.brand;

import com.loopers.domain.brand.Brand;
import com.loopers.domain.brand.BrandRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BrandRepositoryImpl implements BrandRepository {

	private final BrandJpaRepository brandJpaRepository;

	@Override
	public Brand save(final Brand brand) {
		return brandJpaRepository.save(brand);
	}

	@Override
	public Optional<Brand> findById(final Long id) {
		return brandJpaRepository.findById(id);
	}

	@Override
	public List<Brand> findAllBy(final List<Long> ids) {
		return brandJpaRepository.findAllByIdIn(ids);
	}
}
