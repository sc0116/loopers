package com.loopers.domain.stock;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class StockQuantity {

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	protected StockQuantity() {}

	public StockQuantity(final Integer quantity) {
		if (quantity == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 재고 수량은 비어있을 수 없습니다.");
		}

		if (quantity < 0L) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 재고 수량은 음수가 될 수 없습니다.");
		}

		this.quantity = quantity;
	}

	public void decrement(final Integer quantity) {
		if (this.quantity < quantity) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 재고 수량이 부족하여 차감할 수 없습니다.");
		}

		this.quantity -= quantity;
	}
}
