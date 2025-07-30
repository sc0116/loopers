package com.loopers.infrastructure.stock;

import com.loopers.domain.stock.ProductStock;
import com.loopers.domain.stock.ProductStockRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductStockRepositoryImpl implements ProductStockRepository {

	private final ProductStockJpaRepository productStockJpaRepository;

	@Override
	public ProductStock save(final ProductStock productStock) {
		return productStockJpaRepository.save(productStock);
	}

	@Override
	public Optional<ProductStock> findByProductId(final Long productId) {
		return productStockJpaRepository.findByProductId(productId);
	}

	@Override
	public List<ProductStock> findAllByProductId(final List<Long> productIds) {
		return productStockJpaRepository.findAllByProductIdIn(productIds);
	}
}
