package com.loopers.application.order;

import com.loopers.domain.order.OrderCommand;
import com.loopers.domain.order.OrderInfo;
import com.loopers.domain.order.OrderService;
import com.loopers.domain.point.PointCommand;
import com.loopers.domain.point.PointService;
import com.loopers.domain.product.ProductCommand;
import com.loopers.domain.product.ProductInfo;
import com.loopers.domain.product.ProductService;
import com.loopers.domain.stock.ProductStockCommand;
import com.loopers.domain.stock.ProductStockService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class OrderFacade {

	private final OrderService orderService;
	private final ProductService productService;
	private final ProductStockService productStockService;
	private final PointService pointService;

	@Transactional
	public void order(final OrderCriteria.Order criteria) {
		final List<ProductInfo> productInfos = productService.getProducts(criteria.toProductCommand());

		final OrderCommand.Order command = criteria.toCommand(productInfos);
		final OrderInfo orderInfo = orderService.order(command);

		pointService.use(new PointCommand.Use(criteria.userId(), orderInfo.totalPrice().longValue()));

		command.items().forEach(item ->
			productStockService.decrement(new ProductStockCommand.Decrement(item.productId(), item.quantity()))
		);
	}

	public OrderResult getOrder(final OrderCriteria.GetOrder criteria) {
		final OrderInfo orderInfo = orderService.getOrder(criteria.toCommand());
		final List<ProductInfo> productInfos = productService.getProducts(new ProductCommand.GetProducts(orderInfo.getProductIds()));

		return OrderResult.of(orderInfo, productInfos);
	}
}
