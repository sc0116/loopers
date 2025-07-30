package com.loopers.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

	Product save(Product product);

	Optional<Product> findById(Long id);

	List<Product> findAllById(List<Long> ids);
}
