package com.loopers.domain.count;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductCountService {

	private final ProductCountRepository productCountRepository;

	@Transactional
	public void increment(final ProductCountCommand.Increment command) {
		final ProductCount productCount = productCountRepository.findBy(command.productId())
			.orElseGet(() -> new ProductCount(command.productId()));

		productCount.increment(command.countType());

		productCountRepository.save(productCount);
	}

	@Transactional(readOnly = true)
	public Optional<ProductCountInfo> findProductCount(final ProductCountCommand.GetProductCount command) {
		return productCountRepository.findBy(command.productId())
			.map(ProductCountInfo::from);
	}

	@Transactional
	public void decrement(final ProductCountCommand.Decrement command) {
		productCountRepository.findBy(command.productId())
			.ifPresent(productCount -> productCount.decrement(command.countType()));
	}
}
