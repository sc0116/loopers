package com.loopers.infrastructure.product;

import com.loopers.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

	Page<Product> findAllByBrandId_BrandId(Long brandId, Pageable pageable);

	Page<Product> findAllBy(Pageable pageable);
}
