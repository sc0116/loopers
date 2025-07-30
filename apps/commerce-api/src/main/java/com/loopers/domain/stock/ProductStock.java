package com.loopers.domain.stock;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "product_stock")
@Entity
public class ProductStock extends BaseEntity {

	@Column(name = "ref_product_id")
	private Long productId;

	@Embedded
	private StockQuantity quantity;

	protected ProductStock() {}

	public ProductStock(final Long productId, final StockQuantity quantity) {
		if (productId == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 ID는 비어있을 수 없습니다.");
		}

		this.productId = productId;
		this.quantity = quantity;
	}

	public void decrement(final Integer quantity) {
		this.quantity.decrement(quantity);
	}
}
