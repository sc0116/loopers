package com.loopers.domain.product;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional(readOnly = true)
	public ProductInfo getProduct(final ProductCommand.GetProduct command) {
		return productRepository.findById(command.id())
			.map(ProductInfo::from)
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 상품입니다."));
	}
}
