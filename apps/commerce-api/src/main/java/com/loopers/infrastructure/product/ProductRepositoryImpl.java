package com.loopers.infrastructure.product;

import com.loopers.domain.product.Product;
import com.loopers.domain.product.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductRepositoryImpl implements ProductRepository {

	private final ProductJpaRepository productJpaRepository;

	@Override
	public Product save(final Product product) {
		return productJpaRepository.save(product);
	}

	@Override
	public Optional<Product> findById(final Long id) {
		return productJpaRepository.findById(id);
	}
}
