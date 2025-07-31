package com.loopers.application.product;

import com.loopers.domain.brand.BrandCommand;
import com.loopers.domain.brand.BrandInfo;
import com.loopers.domain.brand.BrandService;
import com.loopers.domain.count.ProductCountInfo;
import com.loopers.domain.count.ProductCountService;
import com.loopers.domain.product.ProductInfo;
import com.loopers.domain.product.ProductService;
import com.loopers.domain.stock.ProductStockInfo;
import com.loopers.domain.stock.ProductStockService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductFacade {

	private final ProductService productService;
	private final ProductStockService productStockService;
	private final ProductCountService productCountService;
	private final BrandService brandService;

	public ProductResult getProduct(final ProductCriteria.GetProduct criteria) {
		final ProductInfo productInfo = productService.getProduct(criteria.toProductCommand());
		final Optional<ProductStockInfo> stockInfo = productStockService.findStock(criteria.toStockCommand());
		final Optional<ProductCountInfo> countInfo = productCountService.findProductCount(criteria.toCountCommand());
		final BrandInfo brandInfo = brandService.getBrand(new BrandCommand.GetBrand(productInfo.fetchBrandId()));

		return ProductResult.of(productInfo, stockInfo, countInfo, brandInfo);
	}
}
