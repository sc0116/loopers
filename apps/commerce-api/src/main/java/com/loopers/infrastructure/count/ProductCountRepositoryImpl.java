package com.loopers.infrastructure.count;

import com.loopers.domain.count.ProductCount;
import com.loopers.domain.count.ProductCountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductCountRepositoryImpl implements ProductCountRepository {

	private final ProductCountJpaRepository productCountJpaRepository;

	@Override
	public ProductCount save(final ProductCount productCount) {
		return productCountJpaRepository.save(productCount);
	}

	@Override
	public Optional<ProductCount> findBy(final Long productId) {
		return productCountJpaRepository.findByProductId(productId);
	}
}
