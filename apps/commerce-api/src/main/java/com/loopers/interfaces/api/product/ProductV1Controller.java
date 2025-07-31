package com.loopers.interfaces.api.product;

import com.loopers.application.product.ProductCriteria;
import com.loopers.application.product.ProductFacade;
import com.loopers.application.product.ProductResult;
import com.loopers.interfaces.api.ApiResponse;
import com.loopers.support.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductV1Controller implements ProductV1ApiSpec {

	private final ProductFacade productFacade;

	@GetMapping
	public ApiResponse<ProductDto.V1.SearchProductsResponse> searchProducts(
		@RequestParam(required = false) final Long brandId,
		@RequestParam(defaultValue = "CREATED_AT") ProductSort sort,
		@RequestParam(defaultValue = "ASC") SortOrder sortOrder,
		@PageableDefault(size = 20) final Pageable pageable
	) {
		final ProductResult.SearchProducts productResults =
			productFacade.searchProducts(ProductCriteria.SearchProducts.of(brandId, sort.getDescription(), sortOrder, pageable));

		return ApiResponse.success(ProductDto.V1.SearchProductsResponse.from(productResults));
	}

	@GetMapping("/{productId}")
	@Override
	public ApiResponse<ProductDto.V1.GetResponse> getProduct(
		@PathVariable final Long productId
	) {
		final ProductResult productResult = productFacade.getProduct(new ProductCriteria.GetProduct(productId));

		return ApiResponse.success(ProductDto.V1.GetResponse.from(productResult));
	}
}
