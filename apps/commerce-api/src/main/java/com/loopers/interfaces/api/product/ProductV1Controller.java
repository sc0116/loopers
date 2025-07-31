package com.loopers.interfaces.api.product;

import com.loopers.application.product.ProductCriteria;
import com.loopers.application.product.ProductFacade;
import com.loopers.application.product.ProductResult;
import com.loopers.interfaces.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductV1Controller implements ProductV1ApiSpec {

	private final ProductFacade productFacade;

	@GetMapping("/{productId}")
	@Override
	public ApiResponse<ProductDto.V1.GetResponse> getProduct(
		@PathVariable final Long productId
	) {
		final ProductResult productResult = productFacade.getProduct(new ProductCriteria.GetProduct(productId));

		return ApiResponse.success(ProductDto.V1.GetResponse.from(productResult));
	}
}
