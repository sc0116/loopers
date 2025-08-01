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
	public Optional<Product> findBy(final Long id) {
		return productJpaRepository.findById(id);
	}

	@Override
	public List<Product> findAllBy(final List<Long> ids) {
		return productJpaRepository.findAllById(ids);
	}

	@Override
	public Page<Product> findAllBy(final Long brandId, final Pageable pageable) {
		return productJpaRepository.findAllByBrandId_BrandId(brandId, pageable);
	}

	@Override
	public Page<Product> findAllBy(final Pageable pageable) {
		return productJpaRepository.findAllBy(pageable);
	}
}
