package com.loopers.application.like;

import com.loopers.domain.count.ProductCountService;
import com.loopers.domain.like.LikeInfo;
import com.loopers.domain.like.LikeService;
import com.loopers.domain.product.ProductCommand;
import com.loopers.domain.product.ProductInfo;
import com.loopers.domain.product.ProductService;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductLikeFacade {

	private final LikeService likeService;
	private final ProductService productService;
	private final ProductCountService productCountService;

	@Transactional
	public void like(final LikeCriteria.Like criteria) {
		final Optional<ProductInfo> productInfo = productService.findProduct(criteria.toProductCommand());
		if (productInfo.isEmpty()) {
			throw new CoreException(ErrorType.NOT_FOUND, "좋아요 등록할 상품이 존재하지 않습니다.");
		}

		likeService.like(criteria.toLikeCommand());

		productCountService.increment(criteria.toCountCommand());
	}

	public LikeResult.GetMyProducts getMyProducts(final LikeCriteria.GetMyProducts criteria) {
		final List<LikeInfo> likeInfos = likeService.getMyLikes(criteria.toCommand());

		final List<Long> productIds = likeInfos.stream()
			.map(LikeInfo::fetchTargetId)
			.toList();
		final List<ProductInfo> productInfos = productService.getProducts(new ProductCommand.GetProducts(productIds));

		return LikeResult.GetMyProducts.from(productInfos);
	}

	@Transactional
	public void unlike(final LikeCriteria.Unlike criteria) {
		final Optional<ProductInfo> productInfo = productService.findProduct(criteria.toProductCommand());
		if (productInfo.isEmpty()) {
			throw new CoreException(ErrorType.NOT_FOUND, "좋아요 취소할 상품이 존재하지 않습니다.");
		}

		likeService.unlike(criteria.toLikeCommand());

		productCountService.decrement(criteria.toCountCommand());
	}
}
