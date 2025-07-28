package com.loopers.domain.product;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
@Table(name = "product")
@Entity
public class Product extends BaseEntity {

	@Embedded
	private BrandId brandId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal price;

	private StockQuantity stockQuantity;

	protected Product() {}

	public Product(
		final BrandId brandId,
		final String name,
		final String description,
		final BigDecimal price,
		final StockQuantity stockQuantity
	) {
		validate(name, description, price);

		this.brandId = brandId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	private void validate(final String name, final String description, final BigDecimal price) {
		if (name == null || name.isBlank()) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품명은 비어있을 수 없습니다.");
		}

		if (description == null || description.isBlank()) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 설명은 비어있을 수 없습니다.");
		}

		if (price == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 가격은 비어있을 수 없습니다.");
		}

		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 가격은 음수일 수 없습니다.");
		}
	}
}
