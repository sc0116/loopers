package com.loopers.domain.order;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
@Table(name = "order_item")
@Entity
public class OrderItem extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ref_order_id")
	private Order order;

	@Column(name = "ref_product_id", nullable = false)
	private Long productId;

	@Column(nullable = false)
	private Integer quantity;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal price;

	protected OrderItem() {}

	public OrderItem(final Long productId, final Integer quantity, final BigDecimal price) {
		if (productId == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 ID는 비어있을 수 없습니다.");
		}
		if (quantity == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "주문 상품 수량은 비어있을 수 없습니다.");
		}
		if (quantity < 1) {
			throw new CoreException(ErrorType.BAD_REQUEST, "주문 상품 수량은 최소 1개 이상이어야 합니다.");
		}
		if (price == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "주문 상품 가격은 비어있을 수 없습니다.");
		}
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new CoreException(ErrorType.BAD_REQUEST, "주문 상품 가격은 0원 이상이어야 합니다.");
		}

		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	public BigDecimal calculateTotalPrice() {
		return price.multiply(BigDecimal.valueOf(quantity));
	}

	void initOrder(final Order order) {
		if (this.order == null) {
			this.order = order;
		}
	}
}
