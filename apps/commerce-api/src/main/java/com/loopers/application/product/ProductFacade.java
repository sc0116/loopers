package com.loopers.application.product;

import com.loopers.domain.brand.BrandCommand;
import com.loopers.domain.brand.BrandInfo;
import com.loopers.domain.brand.BrandService;
import com.loopers.domain.count.ProductCountCommand;
import com.loopers.domain.count.ProductCountInfo;
import com.loopers.domain.count.ProductCountService;
import com.loopers.domain.product.ProductInfo;
import com.loopers.domain.product.ProductService;
import com.loopers.domain.stock.ProductStockCommand;
import com.loopers.domain.stock.ProductStockInfo;
import com.loopers.domain.stock.ProductStockService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductFacade {

	private final ProductService productService;
	private final ProductStockService productStockService;
	private final ProductCountService productCountService;
	private final BrandService brandService;

	public ProductResult.SearchProducts searchProducts(final ProductCriteria.SearchProducts criteria) {
		final Page<ProductInfo> productInfos = productService.searchProducts(criteria.toCommand());

		final List<Long> brandIds = productInfos.getContent().stream()
			.map(ProductInfo::fetchBrandId)
			.distinct()
			.toList();
		final List<BrandInfo> brandInfos = brandService.getBrands(new BrandCommand.GetBrands(brandIds));

		final List<Long> productIds = productInfos.getContent().stream()
			.map(ProductInfo::id)
			.toList();
		final List<ProductStockInfo> stockInfos = productStockService.findStocks(new ProductStockCommand.GetStocks(productIds));
		final List<ProductCountInfo> countInfos = productCountService.findProductCounts(new ProductCountCommand.GetProductCounts(productIds));

		return ProductResult.SearchProducts.of(productInfos, stockInfos, countInfos, brandInfos);
	}

	public ProductResult getProduct(final ProductCriteria.GetProduct criteria) {
		final ProductInfo productInfo = productService.getProduct(criteria.toProductCommand());
		final Optional<ProductStockInfo> stockInfo = productStockService.findStock(criteria.toStockCommand());
		final Optional<ProductCountInfo> countInfo = productCountService.findProductCount(criteria.toCountCommand());
		final BrandInfo brandInfo = brandService.getBrand(new BrandCommand.GetBrand(productInfo.fetchBrandId()));

		return ProductResult.of(productInfo, stockInfo, countInfo, brandInfo);
	}
}
