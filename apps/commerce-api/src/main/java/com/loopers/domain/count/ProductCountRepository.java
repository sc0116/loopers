package com.loopers.domain.count;

import java.util.List;
import java.util.Optional;

public interface ProductCountRepository {

	ProductCount save(ProductCount productCount);

	Optional<ProductCount> findBy(Long productId);

	List<ProductCount> findAllBy(List<Long> productIds);
}
