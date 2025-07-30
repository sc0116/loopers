package com.loopers.domain.stock;

import java.util.List;
import java.util.Optional;

public interface ProductStockRepository {

	ProductStock save(ProductStock productStock);

	Optional<ProductStock> findByProductId(Long productId);

	List<ProductStock> findAllByProductId(List<Long> productIds);
}
