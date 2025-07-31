package com.loopers.infrastructure.product;

import com.loopers.domain.product.Product;
import com.loopers.domain.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public List<Product> findAllById(final List<Long> ids) {
		return productJpaRepository.findAllById(ids);
	}

	@Override
	public Page<Product> findAll(final Long brandId, final Pageable pageable) {
		return productJpaRepository.findAllByBrandId_BrandId(brandId, pageable);
	}

	@Override
	public Page<Product> findAll(final Pageable pageable) {
		return productJpaRepository.findAllBy(pageable);
	}
}
