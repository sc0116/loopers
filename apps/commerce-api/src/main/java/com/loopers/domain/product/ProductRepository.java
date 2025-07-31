package com.loopers.domain.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

	Product save(Product product);

	Optional<Product> findById(Long id);

	List<Product> findAllById(List<Long> ids);

	Page<Product> findAll(Long brandId, Pageable pageable);

	Page<Product> findAll(Pageable pageable);
}
