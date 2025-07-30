package com.loopers.domain.product;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional(readOnly = true)
	public ProductInfo getProduct(final ProductCommand.GetProduct command) {
		return findProduct(command)
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 상품입니다."));
	}

	@Transactional(readOnly = true)
	public Optional<ProductInfo> findProduct(final ProductCommand.GetProduct command) {
		return productRepository.findById(command.id())
			.map(ProductInfo::from);
	}

	@Transactional(readOnly = true)
	public List<ProductInfo> getProducts(final ProductCommand.GetProducts command) {
		return productRepository.findAllById(command.ids()).stream()
			.map(ProductInfo::from)
			.toList();
	}
}
