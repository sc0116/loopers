package com.loopers.domain.brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

	Brand save(Brand brand);

	Optional<Brand> findBy(Long id);

	List<Brand> findAllBy(List<Long> ids);
}
