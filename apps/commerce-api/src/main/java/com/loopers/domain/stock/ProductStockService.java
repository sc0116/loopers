package com.loopers.domain.stock;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductStockService {

	private final ProductStockRepository productStockRepository;

	@Transactional(readOnly = true)
	public Optional<ProductStockInfo> findStock(final ProductStockCommand.GetStock command) {
		return productStockRepository.findByProductId(command.productId())
			.map(ProductStockInfo::from);
	}

	@Transactional(readOnly = true)
	public List<ProductStockInfo> findStocks(final ProductStockCommand.GetStocks command) {
		return productStockRepository.findAllByProductId(command.productIds()).stream()
			.map(ProductStockInfo::from)
			.toList();
	}

	@Transactional
	public void decrement(final ProductStockCommand.Decrement command) {
		final ProductStock productStock = productStockRepository.findByProductId(command.productId())
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "상품 재고가 존재하지 않습니다."));

		productStock.decrement(command.quantity());
	}
}
